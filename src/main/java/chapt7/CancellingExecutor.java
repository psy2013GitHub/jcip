package chapt7;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by momo on 16/11/16.
 */
//public class CancellingExecutor extends ThreadPoolExecutor {
//
//    protected<T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
//        if (callable instanceof CancellableTask)
//            return ((CancellableTask<T>) callable).newTask();
//        else
//            return super.newTaskFor(callable);
//    }
//}
