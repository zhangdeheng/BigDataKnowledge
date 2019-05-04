package org.shareing.mythread;

import java.util.Random;

/**
 * 继承方式实现线程
 */
public class ExtendsFunction002 extends Thread{

    String flag;

    public ExtendsFunction002(String flag){
        this.flag=flag;
    }

    public void run(){
        System.out.println("进入 run 方法了。。。");
        String name = Thread.currentThread().getName();
        Random random = new Random();
        try {
            for (int i=0;i<20;i++){
                System.out.println(name+"......"+flag);
                Thread.sleep(random.nextInt(10)*100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ExtendsFunction002 test001 = new ExtendsFunction002("a");
        ExtendsFunction002 test002 = new ExtendsFunction002("b");
        test001.run();
        test002.run();
    }

}
