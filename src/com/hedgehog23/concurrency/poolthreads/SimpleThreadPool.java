package com.hedgehog23.concurrency.poolthreads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.stream.IntStream;

/**
 * @author sergeylapko
 */
public class SimpleThreadPool implements Executor {
    private static final String EXECUTOR_PREFIX = "SimpleThreadPool-";
    private final BlockingQueue<Runnable> tasks;
    private final Thread[] executors;

    public SimpleThreadPool(int executorsNum, int maxTasks) {
        this.executors = new Thread[executorsNum];
        this.tasks = new ArrayBlockingQueue<>(maxTasks);
        IntStream.range(0, executorsNum).forEach(i -> {
            executors[i] = new Thread(handle(), EXECUTOR_PREFIX + i);
            executors[i].start();
        });
    }

    @Override
    public void execute(Runnable command) {
//        // blocking
//        try {
//            tasks.put(command);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }

        // rejection
        if (!tasks.offer(command)) {
            System.out.println(String.format("%s thread can not execute command, task queue is full",
                    Thread.currentThread().getName()));
        }
//
//        // rejection
//        try {
//            tasks.add(command);
//        } catch (IllegalStateException e) {
//            System.out.println(String.format("%s thread can not execute command, task queue is full",
//                    Thread.currentThread().getName()));
//        }
    }

    private Runnable handle() {
        return () -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    Runnable task = tasks.take();
                    task.run();
                } catch (InterruptedException e) {
                    interrupted = true;
                    final Thread currentThread = Thread.currentThread();
                    System.out.println(String.format("%s thread was interrupted", currentThread.getName()));
                    currentThread.interrupt();
                }
            }
        };
    }
}
