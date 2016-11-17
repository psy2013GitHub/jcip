package chapt7;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * Created by momo on 16/11/16.
 */

public interface CancellableTask<T> extends Callable<T> {
    void cancel();
    RunnableFuture<T> newTask();
}
