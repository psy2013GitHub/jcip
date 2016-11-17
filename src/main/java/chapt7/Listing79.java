package chapt7;

/**
 * Created by momo on 16/11/16.
 */

public class Listing79 {

   /* 值得注意得是在线程退出前,如果捕获到InterruptedException, 一定要通过 `Thread.currentThread().interrupt();` 将线程中断状态设置为true, 由线程拥有者决定怎么处理被中断得线程 */
   static class Task implements Runnable {
        public void run() {
            while (true) {
                try {
                    System.out.println("0 Thread.currentThread().isInterrupted(): " + Thread.currentThread().isInterrupted());
                    Thread.sleep(1000);
                } catch (InterruptedException e) { // 在catch中, java自动将当前线程中断状态设置为false
//                    e.printStackTrace();
                    Thread.currentThread().interrupt(); // 这里, 可以重新将中断状态设置为true
                    System.out.println("who awake me, fuxk " + Thread.currentThread().isInterrupted());
                } finally {
                    ;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Task());
        thread.start();
        while (true) {
            thread.interrupt();
            System.out.println("from main thread.isInterrupted()" + thread.isInterrupted());
            Thread.sleep(1000);
        }
    }

}
