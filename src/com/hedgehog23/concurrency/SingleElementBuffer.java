package com.hedgehog23.concurrency;

/**
 * @author sergeylapko
 */
public class SingleElementBuffer<T> {
    private final Object lock = new Object();
    private T element;

    public T get() throws InterruptedException {
        synchronized (lock) {
            while (element == null) {
                lock.wait();
            }

            T val = element;

            element = null;

            lock.notifyAll();

            return val;
        }
    }

    public void put(T elem) throws InterruptedException {
        synchronized (lock) {
            while (element != null) {
                lock.wait();
            }

            element = elem;

            lock.notifyAll();
        }
    }
}
