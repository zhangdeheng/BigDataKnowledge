package org.shareing.myLock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * trylock使用方法
 */
public class MyReentrantLockTryLock {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    ReentrantLock reentrantLock = new ReentrantLock();
    public static void main(String[] args)  {
        final MyReentrantLockTryLock test = new MyReentrantLockTryLock();

        new Thread(){
            public void run() {
                try {
                    test.insert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            public void run() {
                try {
                    test.insert(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void insert(Thread thread) throws InterruptedException {
        if(reentrantLock.tryLock()){
            Thread.sleep(500);
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
        }else {
            System.out.println(thread.getName()+"获取锁失败");
        }

    }

}
