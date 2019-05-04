package org.shareing.myThreadool;

import java.util.concurrent.*;

public class ThreadPoolWithCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 10; i++) {
            //提交任务
            Future<String> submit = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "b--"+Thread.currentThread().getName();
                }
            });
            //从future get方法是被阻塞的 要等到线程返回结果才能继续
            System.out.println(submit.get());
        }
        executorService.shutdown();
    }
}
