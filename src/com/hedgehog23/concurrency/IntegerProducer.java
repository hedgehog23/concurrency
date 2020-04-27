package com.hedgehog23.concurrency;

import java.util.Random;

/**
 * @author sergeylapko
 */
public class IntegerProducer implements Runnable {

    private final Buffer<Integer> buffer;
    private final String name;
    private final int producedValues;

    public IntegerProducer(Buffer<Integer> buffer, String name, int producedValues) {
        this.buffer = buffer;
        this.name = name;
        this.producedValues = producedValues;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < producedValues; i++) {
                Thread.sleep(100 + new Random().nextInt(900));
                Integer val = new Random(System.currentTimeMillis() + name.hashCode() + i).nextInt();
                buffer.put(val);
            }
        } catch (InterruptedException ignore) {

        }
    }
}
