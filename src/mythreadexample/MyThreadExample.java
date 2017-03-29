/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mythreadexample;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rasmus
 */
public class MyThreadExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Simple Runnable task.
        Runnable task = () -> {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("Legend! - " + threadName);
                System.out.println("Wait for it! - " + threadName);
                Thread.sleep(2000);
                System.out.println("Dary! - " + threadName);
                System.out.println("Legendary!! - " + threadName);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThreadExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        };

//        for (int i = 0; i < 10; i++) {
//            new Thread(task).start();
//        }
        //Using a servie to run the task.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(task); //Creates a service that keeps running until it's shutdown.
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.err.println("ERROR - " + ex.getMessage());
        } finally {
            if (!executor.isShutdown()) {
                System.out.println("Didn't work!");
                executor.shutdownNow();
            }
        }
//        Thread thread = new Thread(task);
//        thread.start(); //Setup new thread and execute run.
        System.out.println("Done! - " + Thread.currentThread().getName());

        //A Callable task.
        Callable<Integer> calltask = () -> {

            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i;
                Thread.sleep(1000);
            }

            return sum;
        };

        ExecutorService callExecutor = Executors.newSingleThreadExecutor();
        callExecutor.submit(calltask);

        for (int i = 0; i < 10; i++) {
            System.out.println("Processing...");
        }

        try {
            int result = calltask.call();
            System.out.println("Result: " + result);
        } catch (Exception ex) {
            Logger.getLogger(MyThreadExample.class.getName()).log(Level.SEVERE, null, ex);
        }

        //To get data whenever it is done.
        CompletableFuture.supplyAsync(new DataLoader()).thenAccept(new DataPrinter<>());
        System.out.println("Press ENTER to continue...");
        new Scanner(System.in).nextLine();
        System.out.println("Done!");

        System.exit(0);
    }

}
