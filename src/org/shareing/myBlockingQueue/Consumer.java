package org.shareing.myBlockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10));
            String name = Thread.currentThread().getName();
            System.out.println(name);
            //如果队列为空会阻塞当前线程
            String take = queue.take();
            System.out.println(name+"进行消费"+take);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
