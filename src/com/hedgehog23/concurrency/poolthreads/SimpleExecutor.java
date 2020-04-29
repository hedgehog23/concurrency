package com.hedgehog23.concurrency.poolthreads;

import java.util.concurrent.Executor;

/**
 * @author sergeylapko
 */
public class SimpleExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
