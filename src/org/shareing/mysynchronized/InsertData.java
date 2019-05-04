package org.shareing.mysynchronized;

import java.util.ArrayList;

public class InsertData {

    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    public synchronized void insert(Thread thread) throws InterruptedException {
        for(int i=0;i<5;i++){
            Thread.sleep(100);
            System.out.println(thread.getName()+"在插入数据"+i);
            arrayList.add(i);
        }
    }

    public static void main(String[] args) {
        final InsertData insertData = new InsertData();

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
