package org.shareing.myHbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @Author: zhangdeheng
 * @Date: 2019-05-12 14:03
 * @Version 1.0
 * @Api http://hbase.apache.org/1.2/apidocs/index.html
 */
public class BaseOperate {


    private static final String TABLE_NAME = "blog:article";
    private static final String CF_DEFAULT = "f1";

    /**
     * 创建表
     * @param admin
     * @param table
     * @throws IOException
     */
    public static void createOrOverwrite(Admin admin, HTableDescriptor table) throws IOException {
//        if (admin.tableExists(table.getTableName())) {
//            admin.disableTable(table.getTableName());
//            admin.deleteTable(table.getTableName());
//        }
        admin.createTable(table);
        admin.close();
    }

    public static void deleteTable(Configuration config){
        Admin admin=null;
        try  {
            Connection connection = ConnectionFactory.createConnection(config);
            admin = connection.getAdmin();
            if (admin.tableExists(TableName.valueOf(TABLE_NAME))) {
                admin.disableTable(TableName.valueOf(TABLE_NAME));
                admin.deleteTable(TableName.valueOf(TABLE_NAME));
            }

        }  catch  (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 添加和移除列族
     */
    public static void addFamilyAndRemoveFamily(Configuration config){
        Admin admin=null;
        try  {
            Connection connection = ConnectionFactory.createConnection(config);
            admin = connection.getAdmin();
            if (admin.tableExists(TableName.valueOf(TABLE_NAME))){
                admin.disableTable(TableName.valueOf(TABLE_NAME));
                //get the TableDescriptor of target table
                HTableDescriptor newtd =  admin.getTableDescriptor (TableName.valueOf(Bytes.toBytes (TABLE_NAME)));

                //remove 1 useless column families
                newtd.removeFamily(Bytes.toBytes ( "mycf" ));

                //create HColumnDescriptor for new column family
                HColumnDescriptor newhcd =  new  HColumnDescriptor( "mycf_v1" );
                newhcd.setMaxVersions(10);
                newhcd.setKeepDeletedCells( true );

                //add the new column family(HColumnDescriptor) to HTableDescriptor
                newtd.addFamily(newhcd);

                //modify target table  struture
                admin. modifyTable (TableName.valueOf(Bytes.toBytes (TABLE_NAME)),newtd);

                admin.enableTable(TableName.valueOf(TABLE_NAME));
            }
        }  catch  (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修改现有表的属性 例子是 修改最大的版本
     */
    public static void setMaxVersions(Configuration config){
        Admin admin=null;
        Connection connection =null;
        try  {
             connection=ConnectionFactory.createConnection(config);
            admin = connection.getAdmin();
            if (admin.tableExists(TableName.valueOf(TABLE_NAME))){
                admin.disableTable(TableName.valueOf(TABLE_NAME));
                //get the TableDescriptor of target table
                HTableDescriptor htd =  admin.getTableDescriptor (TableName.valueOf(Bytes.toBytes (TABLE_NAME)));
                HColumnDescriptor infocf = htd.getFamily(Bytes. toBytes ( "mycf_v1" ));
                infocf.setMaxVersions(100);
                //modify target table  struture
                admin.modifyTable(TableName.valueOf(Bytes. toBytes ( TABLE_NAME)),htd);
                admin.enableTable(TableName.valueOf(TABLE_NAME));
            }
        }  catch  (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                admin.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 插入行
     * @param config
     */
    public static void insertRow(Configuration config){
        Table table =null;
        Connection connection=null;
        try {
            connection = ConnectionFactory.createConnection(config);
            table = connection.getTable(TableName.valueOf("blog:article"));
            Put put =  new Put(Bytes.toBytes ( "100003" ));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes ( "name" ), Bytes.toBytes ( "lion"));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes ( "address" ), Bytes.toBytes ( "shangdi"));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes ( "age" ), Bytes.toBytes ( "300"));
            put.setDurability(Durability.FSYNC_WAL );
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除整行
     * @param config
     */
    public static void deleteRow(Configuration config){
        Table table =null;
        Connection connection=null;
        try {
            connection = ConnectionFactory.createConnection(config);
            table = connection.getTable(TableName.valueOf("blog:article"));
            Delete delete = new Delete(Bytes.toBytes("20190512_100001"));
            table.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createSchemaTables(Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {
            HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
            table.addFamily(new HColumnDescriptor(CF_DEFAULT).setCompressionType(Algorithm.NONE));

            System.out.print("Creating table. ");
            createOrOverwrite(admin, table);
            System.out.println(" Done.");
        }
    }

    /**
     * 删除 指定列的最新版本
     */
    public static void deleteColumVersion(Configuration config){
        Table table =null;
        Connection connection=null;
        try {
            connection = ConnectionFactory.createConnection(config);
            table = connection.getTable(TableName.valueOf("blog:article"));
            Delete delete = new Delete(Bytes.toBytes("100003"));
            delete.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("age"));
            table.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                table.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String... args) throws IOException {
        Configuration config = HBaseConfiguration.create();
        //Add any necessary configuration files (hbase-site.xml, core-site.xml)
        config.addResource(new Path(System.getenv("HBASE_CONF_DIR"), "hbase-site.xml"));
        config.addResource(new Path(System.getenv("HADOOP_CONF_DIR"), "core-site.xml"));
//        createSchemaTables(config);
//        deleteTable(config);
//        addFamilyAndRemoveFamily(config);
//        setMaxVersions(config);
//        insertRow(config);
//        deleteRow(config);
        deleteColumVersion(config);
    }
    public static void modifySchema (Configuration config) throws IOException {
        try (Connection connection = ConnectionFactory.createConnection(config);
             Admin admin = connection.getAdmin()) {

            TableName tableName = TableName.valueOf(TABLE_NAME);
            if (!admin.tableExists(tableName)) {
                System.out.println("Table does not exist.");
                System.exit(-1);
            }

            HTableDescriptor table = admin.getTableDescriptor(tableName);

            // Update existing table
            HColumnDescriptor newColumn = new HColumnDescriptor("NEWCF");
            newColumn.setCompactionCompressionType(Algorithm.GZ);
            newColumn.setMaxVersions(HConstants.ALL_VERSIONS);
            admin.addColumn(tableName, newColumn);

            // Update existing column family
            HColumnDescriptor existingColumn = new HColumnDescriptor(CF_DEFAULT);
            existingColumn.setCompactionCompressionType(Algorithm.GZ);
            existingColumn.setMaxVersions(HConstants.ALL_VERSIONS);
            table.modifyFamily(existingColumn);
            admin.modifyTable(tableName, table);

            // Disable an existing table
            admin.disableTable(tableName);

            // Delete an existing column family
            admin.deleteColumn(tableName, CF_DEFAULT.getBytes("UTF-8"));

            // Delete a table (Need to be disabled first)
            admin.deleteTable(tableName);
        }
    }
}
