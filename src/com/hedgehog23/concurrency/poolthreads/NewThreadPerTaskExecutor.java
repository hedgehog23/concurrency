package com.hedgehog23.concurrency.poolthreads;

import java.util.concurrent.Executor;

/**
 * @author sergeylapko
 */
public class NewThreadPerTaskExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
