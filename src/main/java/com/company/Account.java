package com.company;

import java.math.BigDecimal;
import java.util.concurrent.Semaphore;

public class Account {
    private Semaphore isAvailable = new Semaphore(1, true);
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

    public Semaphore getIsAvailable() {
        return this.isAvailable;
    }

    public synchronized void setIsAvailable(Semaphore isAvailable) {
        this.isAvailable = isAvailable;
    }
}
