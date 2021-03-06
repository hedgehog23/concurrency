package com.hedgehog23.concurrency.monitor;

import com.hedgehog23.concurrency.monitor.sync.SingleElementBuffer;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author sergeylapko
 */
public class SingleElementBufferDemo {
    public static void main(String[] args) {
        SingleElementBufferDemo demo = new SingleElementBufferDemo();
        Buffer<Integer> buffer = demo.createBuffer(args);

        Set<Thread> producers = new HashSet<>();
        Set<Thread> consumers = new HashSet<>();

        ThreadGroup producerGroup = new ThreadGroup("producerGroup");
        for (int i = 0; i < 100; i++) {
            final String name = "Producer" + i;
            producers.add(
                    new Thread(producerGroup, new IntegerProducer(buffer, name, 1 + new Random().nextInt(10)), name));
        }

        ThreadGroup consumerGroup = new ThreadGroup("consumerGroup");
        for (int i = 0; i < 10; i++) {
            final String name = "Consumer" + i;
            consumers.add(new Thread(consumerGroup, new IntegerConsumer(buffer), name));
        }

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        while (producers.stream().anyMatch(Thread::isAlive)); // spin lock

        consumerGroup.interrupt();
    }

    private Buffer<Integer> createBuffer(String[] args) {
        if (args != null && args.length > 0) {
            String arg0 = args[0];

            switch (arg0) {
                case "sync":
                    return new SingleElementBuffer<>();
                case "lock":
                    return new com.hedgehog23.concurrency.monitor.lock.SingleElementBuffer<>();
            }
        }

        return new SingleElementBuffer<>();
    }
}
