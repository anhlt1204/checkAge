package com.edso;

import java.io.FileWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static ReentrantLock lock = new ReentrantLock();
    public static int count = 0;

    public static void main(String[] args) {
        Dad dad = new Dad(lock);
        Mom mom = new Mom(lock);
        Ubnd ubnd = new Ubnd(lock);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(dad);
        executor.execute(mom);
        executor.execute(ubnd);

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("==>" + count);
        boolean checkAge = false;
        if (count >= 2) {
            checkAge = true;
        }

        write(checkAge);
    }

    public static void write(boolean check) {
        try {
            FileWriter fw = new FileWriter("result.txt");
            fw.write(String.valueOf(check));
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Success...");
    }
}
