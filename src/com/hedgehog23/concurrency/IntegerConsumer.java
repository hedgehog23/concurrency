package com.hedgehog23.concurrency;

/**
 * @author sergeylapko
 */
public class IntegerConsumer implements Runnable {
    private final SingleElementBuffer<Integer> buffer;
    private int consumedValues;

    public IntegerConsumer(SingleElementBuffer<Integer> buffer) {
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
