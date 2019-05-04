package org.shareing.mysynchronized;

import java.util.ArrayList;

public class InsertDataStatic {

    public synchronized void insert() {
        System.out.println("执行insert");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行insert完毕");
    }
    public synchronized static void insert1() {
        System.out.println("执行insert1");
        System.out.println("执行insert1完毕");
    }

    public static void main(String[] args) {
        final InsertDataStatic insertData = new InsertDataStatic();

        new Thread() {
            public void run() {
                insertData.insert();
            };
        }.start();


        new Thread() {
            public void run() {
                insertData.insert1();
            };
        }.start();
    }

}
