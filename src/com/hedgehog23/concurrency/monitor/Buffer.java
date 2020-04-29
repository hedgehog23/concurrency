package com.hedgehog23.concurrency.monitor;

/**
 * @author sergeylapko
 */
public interface Buffer<T> {
    T get() throws InterruptedException;

    void put(T elem) throws InterruptedException;
}
