package chapt14;

/**
 * Created by momo on 16/11/29.
 *
 * 添加generation得原因:
 *    因为, 从 `open.notifyAll释放锁` 到 `await发现isOpen==true后, wait等待锁` 间隙中, 可能有其他线程调用了close, 导致latch已经关闭
 *
 */
public class ThreadGate {
    private Object _lock = new Object();
    private boolean isOpen=false;
    private int generation;

    public void close() {
        synchronized (this) {
            isOpen = false;
        }
    }

    public void open() {
        synchronized (this) {
            generation++;
            isOpen = true;
            notifyAll(); // 释放锁, 通知所有threads
        }
    }

    public void await() throws InterruptedException {
        synchronized (this) {
            int arrivalGeneration = generation;
            while (!isOpen && arrivalGeneration == generation) // 试试, `while (!isOpen)` 会发生什么
               wait(); // 每次wait都会release this, 然后等待this
        }
    }

}
