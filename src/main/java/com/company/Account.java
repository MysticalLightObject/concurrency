package com.company;

import java.math.BigDecimal;
import java.util.concurrent.Semaphore;

public class Account {
    private Semaphore semaphore = new Semaphore(1, true);
    private int accountId;
    private String ownerName;
    private BigDecimal availableAmount;
    private String fileName;

    public int getAccountId() {
        return this.accountId;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public BigDecimal getAvailableAmount() {
        return this.availableAmount;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Semaphore getSemaphore() {
        return this.semaphore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Account id: " + accountId);
        sb.append("\nOwner name: " + ownerName);
        sb.append("\nAvailable amount: " + availableAmount.setScale(2, BigDecimal.ROUND_DOWN));
        return sb.toString();
    }

}
