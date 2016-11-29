package chapt10;

import java.util.Random;

/**
 * Created by momo on 16/11/23.
 */
public class DemonstrateDeadlock {
    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITERATIONS = 1000000;

    public static void main(String[] args) {
        final Random rnd = new Random();

        final Account[] accounts = new Account[NUM_ACCOUNTS];
        for (int i = 0; i < accounts.length; i++)
            accounts[i] = new Account();

        class TransferThread extends Thread {
            public void run() {
                for (int i=0; i<NUM_ITERATIONS; i++) {
                    System.out.println(Thread.currentThread().getId() + ":" + i);
                    try {
                        int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
                        int toAcct = rnd.nextInt(NUM_ACCOUNTS);
                        DollarAmount amount = new DollarAmount(rnd.nextInt(1000));
                        transferMoney(accounts[fromAcct], accounts[toAcct], amount);
                    } catch (InsufficientFundsException e) {
                        ;
                    }
                }
            }
        }
        for (int i = 0; i < NUM_THREADS; i++)
            new TransferThread().start();
    }

    private static final Object tieLock = new Object();
    public static void transferMoney(final Account fromAcct, final Account toAcct,
                              final DollarAmount amount) throws InsufficientFundsException {
        class Helper {
            public void transfer() throws InsufficientFundsException {
                if (fromAcct.getBalance().compareTo(amount) < 0) throw new InsufficientFundsException();
                else {
                    fromAcct.debit(amount);
                    toAcct.credit(amount);
                } }
        }
        int fromHash = System.identityHashCode(fromAcct); int toHash = System.identityHashCode(toAcct);
        if (fromHash < toHash) {
            synchronized (fromAcct) {
                synchronized (toAcct) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAcct) {
                synchronized (fromAcct) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAcct) {
                    synchronized (toAcct) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

}
