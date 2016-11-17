package chapt7;

//import sun.jvm.hotspot.runtime.Thread;

import java.util.concurrent.*;

/**
 * Created by momo on 16/11/16.
 */
public class Listing710 {

    static class Task implements Runnable { // 控制线程

        private boolean ever_interrupted = false;
        private Throwable throwable = null;

        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                this.ever_interrupted = true;
            } catch (Throwable t) {
                this.throwable = t;
            } finally {
                if (this.ever_interrupted)
                    Thread.currentThread().interrupt();
            }
            if (this.throwable != null) {
                throw this.throwable; // fixme
            }
        }
    }

    public static void main(String[] args) {

        ExecutorService exec_pool = new ThreadPoolExecutor(1, 1, 1000L, TimeUnit.MICROSECONDS, new LinkedBlockingQueue());
        Future<?> task = exec_pool.submit(new Task());

        try {
            task.get(10000, TimeUnit.MILLISECONDS); // 等待Runnable结束或Callable返回结果
        } catch (TimeoutException e1) {
            System.out.println("TimeoutException");
        } catch (ExecutionException e2) {
            System.out.println("ExecutionException");
        } catch (InterruptedException e3) {
            System.out.println("InterruptedException");
        } finally {
            System.out.println("cancelling RunningTask...");
            task.cancel(true);
            System.out.println("cancelling RunningTask done");
        }
    }

}
