package com.edso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class Dad extends Thread{

    private ReentrantLock lock;
    public Dad(ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            if (checkAge(readFile()))
                Main.count++;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private int readFile() throws IOException {
        int age = 0;
        FileReader frd = null;
        BufferedReader bufR = null;

        try {
            frd = new FileReader("dad.txt");
            bufR = new BufferedReader(frd);
            String line;
            while ((line = bufR.readLine()) != null)
            {
                age = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bufR != null ) {
                    bufR.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return age;
    }

    private boolean checkAge(int age) {
        return age == 21;
    }
}
