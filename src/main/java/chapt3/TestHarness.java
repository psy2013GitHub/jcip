package chapt3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RunnableFuture;

/**
 * Created by momo on 16/11/14.
 */
public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1); final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) { }
                }
            };
            t.start();
        }
        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end-start;
    }

    static class Test implements Runnable {
        public void run() {
             int x = 4*3;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestHarness inst = new TestHarness();
        System.out.println(inst.timeTasks(20, new Test())/20.0);
    }

}
