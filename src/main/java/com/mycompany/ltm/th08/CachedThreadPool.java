package com.mycompany.ltm.th08;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public static void main(String[] args) {
        Executor executor = Executors.newCachedThreadPool();
        for(int i = 0; i <=7; i++){
            Runnable worker = new Worker(Integer.toString(i));
            executor.execute(worker);
        }
        ((ExecutorService) executor).shutdown();
        while(!((ExecutorService) executor).isTerminated()){}
        System.out.println("All threads finished");
    }
}
