package com.edso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class Ubnd extends Thread{

    private ReentrantLock lock;

    public Ubnd(ReentrantLock lock) {
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

    private Date readFile() throws IOException {
        Date birthday = new Date();
        FileReader frd = null;
        BufferedReader bufR = null;

        try {
            frd = new FileReader("ubnd.txt");
            bufR = new BufferedReader(frd);
            String line;
            while ((line = bufR.readLine()) != null)
            {
                birthday =new SimpleDateFormat("dd/MM/yyyy").parse(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufR != null ) {
                    bufR.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return birthday;

    }

    private boolean checkAge(Date birthday) {
        Date date = new Date();
        long diff = date.getTime() - birthday.getTime();
        long age = diff / (1000 * 60 * 60 * 24)/ 365;
        return age == 21;
    }
}
