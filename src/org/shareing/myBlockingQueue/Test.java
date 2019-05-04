package org.shareing.myBlockingQueue;

import java.util.concurrent.LinkedBlockingDeque;

public class Test {
    public static void main(String[] args) {
        LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<>(2);
        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);
        for (int i = 0; i < 3; i++) {
            new Thread(producer,"Producer"+(i+1)).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(consumer,"Consumer"+(i+1)).start();
        }
    }
}
