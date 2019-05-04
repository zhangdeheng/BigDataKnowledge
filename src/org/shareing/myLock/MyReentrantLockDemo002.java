package org.shareing.myLock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁  正确的示范例子
 */
public class MyReentrantLockDemo002 {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    ReentrantLock reentrantLock = new ReentrantLock();
    public static void main(String[] args)  {
        final MyReentrantLockDemo002 test = new MyReentrantLockDemo002();

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
