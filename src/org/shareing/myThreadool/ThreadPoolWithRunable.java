package org.shareing.myThreadool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolWithRunable {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            //提交任务
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread name:"+Thread.currentThread().getName());
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }
}
