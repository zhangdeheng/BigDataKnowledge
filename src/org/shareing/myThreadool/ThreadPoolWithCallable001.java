package org.shareing.myThreadool;

import java.util.ArrayList;
import java.util.concurrent.*;

public class ThreadPoolWithCallable001 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();    //Future 相当于是用来存放Executor执行的结果的一种容器
        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> fs : results) {
            if (fs.isDone()) {
                System.out.println(fs.get());
            } else {
                System.out.println("Future result is not yet complete");
            }
        }
        exec.shutdown();
    }
}
