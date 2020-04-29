package com.hedgehog23.concurrency.poolthreads;

import java.util.Random;

/**
 * @author sergeylapko
 */
public class Task implements Runnable {
    @Override
    public void run() {
        System.out.println(String.format("%s executes task", Thread.currentThread().getName()));
        try {
            Thread.sleep(100 + new Random(System.currentTimeMillis()).nextInt(2900)); // 0.1s ... 3s
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(String.format("%s finishes task", Thread.currentThread().getName()));
    }
}
