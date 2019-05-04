package org.shareing.mysynchronized;

import java.util.ArrayList;

public class InsertDataThis {

    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    public void insert(Thread thread) throws InterruptedException {
        synchronized (this){
            for(int i=0;i<5;i++){
                Thread.sleep(100);
                System.out.println(thread.getName()+"在插入数据"+i);
                arrayList.add(i);
            }
        }

    }

    public static void main(String[] args) {
        final InsertDataThis insertData = new InsertDataThis();

        new Thread() {
            public void run() {
                try {
                    insertData.insert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();


        new Thread() {
            public void run() {
                try {
                    insertData.insert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

}
