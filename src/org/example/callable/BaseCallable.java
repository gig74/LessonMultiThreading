package org.example.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class BaseCallable {
    public static void main(String []args) throws Exception {
        Callable<Integer> task = () -> {
            return 555;//"Hello, World!";
        };
        FutureTask<Integer> future = new FutureTask<>(task);
        new Thread(future).start();
        Thread.sleep(5000);
        System.out.println(future.get());
    }
}
