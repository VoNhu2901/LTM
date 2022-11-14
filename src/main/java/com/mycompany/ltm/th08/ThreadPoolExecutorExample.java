package com.mycompany.ltm.th08;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorExample {
    public static void main(String[] args) {
        int corePoolSize = 1;
        int maximumPoolSize = 3;
        int queueCapacity = 8;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueCapacity));
        for(int i = 0; i <=10; i++){
            executor.execute(new Worker("Request-"+i));
        }
        executor.shutdown();
        while(!executor.isTerminated()){}
        System.out.println("All threads finished");
    }
}
