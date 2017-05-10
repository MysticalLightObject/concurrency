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
        while (true) {
            if (acc1.getAccountId() == acc2.getAccountId()) {
                acc2.getSemaphore().release();
                acc2 = getRandomAvailableAccount();
            } else {
                break;
            }
        }
        AccountsAndAmounts accountsAndAmount = getAvailableMoneyToTransfer(acc1, acc2);
        Transaction transaction = performTranscation(accountsAndAmount);

        System.out.println("Thread: " + t.getName() + "\n" + "Counter: " + Main.counter.decrementAndGet());
    }

    private Account getRandomAvailableAccount() {
        int size = accounts.size();
        int randomAccountNumber = ThreadLocalRandom.current().nextInt(0, size - 1);
        Account account = accounts.get(randomAccountNumber);
        account.getSemaphore().acquireUninterruptibly();
        return account;
    }

    private AccountsAndAmounts getAvailableMoneyToTransfer(
            Account acc1,
            Account acc2
    ) {
        int randomAccountNumber = ThreadLocalRandom.current().nextInt(0, 1);
        BigDecimal random = new BigDecimal(Math.random());
        AccountsAndAmounts accountsAndAmounts = new AccountsAndAmounts();

        if (randomAccountNumber == 0) {
            accountsAndAmounts.setAccountTo(acc1);
            accountsAndAmounts.setAccountFrom(acc2);
            accountsAndAmounts.setAmountToTransfer(random.multiply(acc1.getAvailableAmount()));
            return accountsAndAmounts;
        } else {
            accountsAndAmounts.setAccountTo(acc2);
            accountsAndAmounts.setAccountFrom(acc1);
            accountsAndAmounts.setAmountToTransfer(random.multiply(acc2.getAvailableAmount()));
            return accountsAndAmounts;
        }
    }

    private Transaction performTranscation(
            AccountsAndAmounts accountFromAndAmount
    ) {
        Account accountFrom = accountFromAndAmount.getAccountFrom();
        Account accountTo = accountFromAndAmount.getAccountTo();
        BigDecimal amountToTransfer = accountFromAndAmount.getAmountToTransfer();
        accountFrom.setAvailableAmount(accountFrom.getAvailableAmount().subtract(amountToTransfer));
        accountTo.setAvailableAmount(accountTo.getAvailableAmount().add(amountToTransfer));

        return new Transaction(accountFrom, accountTo, amountToTransfer);
    }

    @Override
    public void run() {
        perform();
    }
}

class AccountsAndAmounts {
    private Account accountFrom;
    private Account accountTo;
    private BigDecimal amountToTransfer;

    public BigDecimal getAmountToTransfer() {
        return amountToTransfer;
    }

    public void setAmountToTransfer(BigDecimal amountToTransfer) {
        this.amountToTransfer = amountToTransfer;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account account) {
        this.accountTo = account;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }
}
