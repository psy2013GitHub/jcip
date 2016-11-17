package chapt7;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by momo on 16/11/17.
 * 场景: 多个producer, 一个consumer;
 * 策略1:
 *      方法: 通过一个stop变量来同步producer, consumer;
 *      问题: 可能producer正在被queue阻塞, push完才看到stop;
 * 策略2:
 *      方法: 继续增加counter变量, 只有当 `stop&&counter==0` 才break
 */
public class LogService {

    private final LogThread logger;
    private final BlockingQueue<String> queue;
    private volatile boolean stop = false;
    private long msg_counter = 0;
    private final Object _lock = new Object();

    LogService() {
        logger = new LogThread();
        queue = new LinkedBlockingDeque<String>();
        start();
    }

    // factory method, in case of partial publication
    public static LogService getInstance() {
        return new LogService();
    }

    public void start() {
        logger.start();
    }

    public void stop() {
        this.stop = true;
    }

    public void log(String msg) {
        if (stop) {
            throw new IllegalStateException("fuxk");
        }
        queue.add(msg);
        synchronized (_lock) {
            msg_counter++;
        }
    }

    public class LogThread extends Thread {
        public void run() {
            try {
                while (true) {
                    try {
                        if (stop) {
                            synchronized (_lock) {
                                if (msg_counter == 0)
                                    break;
                            }
                        }
                        String msg = queue.take();
                        synchronized (_lock) {
                            msg_counter--;
                        }
                        System.out.println(msg);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } finally {
                ;
            }
        }
    }

}
