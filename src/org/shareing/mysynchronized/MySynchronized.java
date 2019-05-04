package org.shareing.mysynchronized;

import java.sql.SQLOutput;

public class MySynchronized {
    public static void main(String[] args) {
        MySynchronized mySynchronized = new MySynchronized();
        MySynchronized mySynchronized1 = new MySynchronized();
        new Thread("thread"){
            public void run(){
                synchronized (mySynchronized){
                    System.out.println(this.getName()+" start");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(this.getName()+" 醒了");
                    System.out.println(this.getName()+" end");
                }
            }
        }.start();
        new Thread("thread1"){
            @Override
            public void run() {
                //可以修改mySynchronized1  为 mySynchronized 看效果
                synchronized (mySynchronized){
                    System.out.println(this.getName()+" start");
                    System.out.println(this.getName()+" end");
                }
            }
        }.start();
    }
}
