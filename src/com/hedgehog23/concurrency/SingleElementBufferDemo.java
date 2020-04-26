package com.hedgehog23.concurrency;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author sergeylapko
 */
public class SingleElementBufferDemo {
    public static void main(String[] args) throws InterruptedException {
        SingleElementBuffer<Integer> buffer = new SingleElementBuffer<>();

        Set<Thread> producers = new HashSet<>();
        Set<Thread> consumers = new HashSet<>();

        ThreadGroup producerGroup = new ThreadGroup("producerGroup");
        for (int i = 0; i < 100; i++) {
            final String name = "Producer" + i;
            producers.add(new Thread(producerGroup, new IntegerProducer(buffer, name, 1 + new Random().nextInt(10)), name));
        }

        ThreadGroup consumerGroup = new ThreadGroup("consumerGroup");
        for (int i = 0; i < 10; i++) {
            final String name = "Consumer" + i;
            consumers.add(new Thread(consumerGroup, new IntegerConsumer(buffer), name));
        }

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        Thread.sleep(1000);

        while (producers.stream().anyMatch(Thread::isAlive));

        consumerGroup.interrupt();
    }
}
