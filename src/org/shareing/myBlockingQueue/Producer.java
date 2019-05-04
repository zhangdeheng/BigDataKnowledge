package org.shareing.myBlockingQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10));
            System.out.println("I have made a product:"+Thread.currentThread().getName());
            String temp="A Product,生产线程，"+Thread.currentThread().getName();
            queue.put(temp);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
