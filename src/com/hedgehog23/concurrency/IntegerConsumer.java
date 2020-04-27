package com.hedgehog23.concurrency;

/**
 * @author sergeylapko
 */
public class IntegerConsumer implements Runnable {
    private final Buffer<Integer> buffer;
    private int consumedValues;

    public IntegerConsumer(Buffer<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer.get();
                consumedValues++;
            }
        } catch (InterruptedException ignore) {
            System.out.println(Thread.currentThread().getName() + ": " + consumedValues);
        }
    }
}
