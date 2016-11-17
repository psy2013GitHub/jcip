package chapt7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Created by momo on 16/11/17.
 */
public class LogServiceByExecutor {
    private final ExecutorService exec = newSingleThreadExecutor();
    public void start() { }
    public void stop() throws InterruptedException { try {
        exec.shutdown();
        exec.awaitTermination(10000, TimeUnit.MICROSECONDS);
    } finally {
        writer.close();
    } }
    public void log(String msg) { try {
        exec.execute(new WriteTask(msg));
    } catch (RejectedExecutionException ignored) { }
    }
}
