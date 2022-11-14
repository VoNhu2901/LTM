package com.mycompany.ltm.th08;


import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//class Worker implements Runnable {
//    private String task;
//    public Worker(String task) {this.task = task;}
//
//    public void run() {
//        System.out.println(Thread.currentThread().getName()+" starting. Task = "+task);
//        try{
//            Random random = new Random();
//            Thread.sleep(random.nextInt(random.nextInt(3000)+500)); //sleep between 500 - 3500ms
//        }catch(InterruptedException e){e.printStackTrace();}
//        System.out.println(Thread.currentThread().getName()+" finished.");
//    }
//}

public class FixedThreadPool {
    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(3);
        for(int i = 0; i <=7; i++){
            Runnable worker = new Worker(Integer.toString(i));
            executor.execute(worker);
        }
        ((ExecutorService) executor).shutdown();
        while(!((ExecutorService) executor).isTerminated()){}
        System.out.println("All threads finished");
    }
}
