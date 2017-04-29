package com.company;


public class AccountManager implements Runnable {
    Account accountFrom;
    Account accountTo;
    private Thread t;

    public void performTransaction() {
        t = Thread.currentThread();
        if (Main.counter.get() == 0) {
            return;
        }
        System.out.println("Thread: " + t.getName() + "\n" + "Counter: " + Main.counter.decrementAndGet());
    }

    @Override
    public void run() {
        performTransaction();
    }

}
