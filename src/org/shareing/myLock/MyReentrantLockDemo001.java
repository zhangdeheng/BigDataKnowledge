package org.shareing.myLock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁  错误的示范例子
 */
public class MyReentrantLockDemo001 {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    public static void main(String[] args)  {
        final MyReentrantLockDemo001 test = new MyReentrantLockDemo001();

        new Thread(){
            public void run() {
                test.insert(Thread.currentThread());
            }
        }.start();

        new Thread(){
            public void run() {
                test.insert(Thread.currentThread());
            }
        }.start();
    }
    public void insert(Thread thread){
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try {
            System.out.println(thread.getName()+"得到了锁");
            for(int i=0;i<5;i++) {
                arrayList.add(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(thread.getName()+"释放了锁");
            reentrantLock.unlock();
        }
    }

}
