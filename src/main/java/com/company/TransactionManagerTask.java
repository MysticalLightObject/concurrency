package com.company;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TransactionManagerTask implements Runnable {
    private Thread t;
    private ArrayList<Account> accounts;

    public TransactionManagerTask(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void perform() {
        t = Thread.currentThread();
        if (Main.counter.get() == 0) {
            return;
        }

        Account acc1 = getRandomAvailableAccount();
        Account acc2 = getRandomAvailableAccount();

        Transaction transaction = getAvailableMoneyToTransfer(acc1, acc2);
        performTranscation(transaction);

        acc1.getSemaphore().release();
        acc2.getSemaphore().release();
    }

    private synchronized Account getRandomAvailableAccount() {
        int size = accounts.size();
        Account account = null;

        boolean notAcquired = true;
        while (notAcquired) {
            int randomAccountNumber = ThreadLocalRandom.current().nextInt(0, size - 1);
            account = accounts.get(randomAccountNumber);
            try {
                if (account.getSemaphore().availablePermits() == 1) {
                    account.getSemaphore().acquire();
                } else {
                    continue;
                }
                System.out.println("Acquiring lock in thread: " + t.getName() + "\nOn account id: " + account.getAccountId() + "\n");
                System.out.println("Thread: " + t.getName() + "\n" + "Counter: " + Main.counter.getAndDecrement());
                notAcquired = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return account;
    }

    private Transaction getAvailableMoneyToTransfer(
            Account acc1,
            Account acc2
    ) {
        int randomAccountNumber = ThreadLocalRandom.current().nextInt(0, 1);
        BigDecimal random = new BigDecimal(Math.random());
        Transaction transaction = new Transaction();

        if (randomAccountNumber == 0) {
            transaction.setToAccount(acc1);
            transaction.setFromAccount(acc2);
            transaction.setAmount(random.multiply(acc1.getAvailableAmount()));
            return transaction;
        } else {
            transaction.setToAccount(acc2);
            transaction.setFromAccount(acc1);
            transaction.setAmount(random.multiply(acc2.getAvailableAmount()));
            return transaction;
        }
    }

    private Transaction performTranscation(
            Transaction transaction
    ) {
        Account accountFrom = transaction.getFromAccount();
        Account accountTo = transaction.getToAccount();
        BigDecimal amountToTransfer = transaction.getAmount();
        accountFrom.setAvailableAmount(accountFrom.getAvailableAmount().subtract(amountToTransfer));
        accountTo.setAvailableAmount(accountTo.getAvailableAmount().add(amountToTransfer));

        return new Transaction(accountFrom, accountTo, amountToTransfer);
    }

    @Override
    public void run() {
        perform();
    }
}

