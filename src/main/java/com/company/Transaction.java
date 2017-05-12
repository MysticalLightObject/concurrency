package com.company;

import java.math.BigDecimal;

public class Transaction {
    private Account fromAccount;
    private Account toAccount;
    private BigDecimal amount;
    private String uuid;

    public String getUUID() {
        return uuid;
    }

    public Transaction(
            Account fromAccount,
            Account toAccount,
            BigDecimal amount,
            String uuid
    ) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.uuid = uuid;
    }

    public Transaction() {

    }

    public Account getFromAccount() {
        return this.fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return this.toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
