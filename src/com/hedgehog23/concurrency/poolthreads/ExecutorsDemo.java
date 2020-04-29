package com.hedgehog23.concurrency.poolthreads;

import java.util.concurrent.Executor;

/**
 * @author sergeylapko
 */
public class ExecutorsDemo {
    public static void main(String[] args) {
        ExecutorsDemo demo = new ExecutorsDemo();
        Executor executor = demo.getExecutor(args);
        for (int i = 0; i < 50; i++) {
            executor.execute(new Task());
        }

        System.out.println("Finish submit tasks");
    }

    private Executor getExecutor(String[] args) {
        if (args != null && args.length > 0) {
            String arg0 = args[0];

            switch (arg0) {
                case "newThread":
                    return new NewThreadPerTaskExecutor();
                case "simple":
                    return new SimpleExecutor();
                case "pool":
                    int executorsNum = args.length > 1 && isInteger(args[1]) ? Integer.parseInt(args[1]) : 4;
                    int maxTasks = args.length > 2 && isInteger(args[2]) ? Integer.parseInt(args[2]) : 4;
                    return new SimpleThreadPool(executorsNum, maxTasks);
            }
        }

        return new SimpleExecutor();
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
