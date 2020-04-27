package com.hedgehog23.concurrency.lock;

import com.hedgehog23.concurrency.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sergeylapko
 */
public class SingleElementBuffer<T> implements Buffer<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition getCondition = lock.newCondition();
    private final Condition putCondition = lock.newCondition();
    private T element;

    public T get() throws InterruptedException {
        final String threadName = Thread.currentThread().getName();
        lock.lock();
        try {
            while (element == null) {
                System.out.println(String.format("%s await to get", threadName));
                getCondition.await();
                System.out.println(String.format("%s woke up to get", threadName));
            }

            System.out.println(String.format("%s get %s", threadName, element));

            putCondition.signal();

            T elem = element;
            element = null;
            return elem;
        } finally {
            lock.unlock();
        }
    }

    public void put(T elem) throws InterruptedException {
        final String threadName = Thread.currentThread().getName();
        lock.lock();
        try {
            while (element != null) {
                System.out.println(String.format("%s await to put", threadName));
                putCondition.await();
                System.out.println(String.format("%s woke up to put", threadName));
            }

            System.out.println(String.format("%s put %s", threadName, elem));

            getCondition.signal();

            this.element = elem;
        } finally {
            lock.unlock();
        }
    }
}
