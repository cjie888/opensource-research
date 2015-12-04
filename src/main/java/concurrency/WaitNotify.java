package concurrency;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by hucj on 2015/11/18.
 */
public class WaitNotify {

    static boolean flag = true;

    static Object lock = new Object();

    public static void main(String[] args) throws  Exception{
        Thread waitThread = new Thread(new Wait(), "waitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "notifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                while(flag) {
                    try {
                        System.out.println(Thread.currentThread() + "flat is true.running@"
                                + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println(Thread.currentThread() + "flat is false.running@"+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }
    static class Notify implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread() + "hold lock.notify@"
                            + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    lock.notifyAll();
                    flag = false;
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
            }
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock again.sleep@"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
