package org.shareing.myLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *和 synchronized 对比
 */
public class ReentrantReadWriteLock002 {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args)  {
        final ReentrantReadWriteLock002 test = new ReentrantReadWriteLock002();

        new Thread(){
            public void run() {
                test.get(Thread.currentThread());
            };
        }.start();

        new Thread(){
            public void run() {
                test.get(Thread.currentThread());
            };
        }.start();

    }

    public  void get(Thread thread) {
        rwl.readLock().lock();
        try {
            long start = System.currentTimeMillis();
            for (int i=0;i<5;i++){
                System.out.println(thread.getName()+"正在进行读操作");
                Thread.sleep(100);
            }
            System.out.println(thread.getName()+"读操作完毕");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwl.readLock().unlock();
        }

    }
}
