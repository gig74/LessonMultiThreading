package org.example;

import java.util.concurrent.TimeUnit;

public class TestWaitAndNotify {
    private static Object object = new Object();
    public static void main(String[] args) throws InterruptedException {
        Runnable unlocker = () -> {
            try {
//               Thread.sleep(10000);
                TimeUnit.SECONDS.sleep(10);
                synchronized (object) {
                    object.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread unlockerThread = new Thread(unlocker);
        unlockerThread.setDaemon(true);
        unlockerThread.start();
//        new Thread(unlocker).setDaemon(true);
        synchronized (object) {
            System.out.println("Блокирую объект");
            object.wait();
        }
        System.out.println("Нить main завершена");

    }
}
