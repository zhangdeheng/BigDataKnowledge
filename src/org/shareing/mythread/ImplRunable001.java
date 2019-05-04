package org.shareing.mythread;

/**
 * 通过实现runnable接口，实现线程
 */
public class ImplRunable001 implements Runnable {

    int x;

    public ImplRunable001(int x) {
        this.x = x;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("线程"+name+"开始执行");
        try{
            for (int i=0;i<10;i++){
                System.out.println(x);
                Thread.sleep(100);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread thread001 = new Thread(new ImplRunable001(1));
        Thread thread002 = new Thread(new ImplRunable001(2));
        thread001.start();
        thread002.start();
    }
}
