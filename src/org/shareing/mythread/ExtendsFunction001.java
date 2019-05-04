package org.shareing.mythread;

/**
 * 继承方式实现线程
 */
public class ExtendsFunction001 extends Thread{

    public void run(){
        System.out.println("进入 run 方法了。。。");
    }

    public static void main(String[] args) {
        ExtendsFunction001 test001 = new ExtendsFunction001();
        /*
        和 test001.run()区别是：
        1.start 是开启了一个新的线程，新的空间，有自己的计数器。
        2.直接调用run方法，是在主线程中运行，没有开启新的线程。调用run 就是普通方法的调用
         */
        test001.start();
    }

}
