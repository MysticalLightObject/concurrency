package com.company;

import java.math.BigDecimal;
import java.util.Formatter;
import java.util.Locale;

public class CustomFormatter {

    public static String format(Transaction trBefore, Transaction trDone, BigDecimal initialAmountFrom, BigDecimal initialAmountTo) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        String headerFormat = "\n|%-20s |%-42s |%-40s\n";
        String secondHeaderFormat = "|%-20s |%-20s |%-20s |%-20s |%-20s\n";
        String accountIdFormat = "|%-20s |%-20d |%-20d |%-20d |%-20d\n";
        String accountOwnerFormat = "|%-20s |%-20s |%-20s |%-20s |%-20s\n";
        String accountAmountFormat = "|%-20s |%-20.2f |%-20.2f |%-20.2f |%-20.2f\n";
        String totalTransferredFormat = "|%-20s |%-80.2f\n";
        String transactionUUIDFormat = "|%-20s |%s\n";

        formatter.format(headerFormat, "STAT", "FROM ACCOUNT", "TO ACCOUNT");
        formatter.format(secondHeaderFormat, "------", "Before", "After", "Before", "After");
        formatter.format(accountIdFormat,
                "AccountId",
                trBefore.getFromAccount().getAccountId(),
                trDone.getFromAccount().getAccountId(),
                trBefore.getToAccount().getAccountId(),
                trDone.getToAccount().getAccountId());
        formatter.format(accountOwnerFormat,
                "Owner",
                trBefore.getFromAccount().getOwnerName(),
                trDone.getFromAccount().getOwnerName(),
                trBefore.getToAccount().getOwnerName(),
                trDone.getToAccount().getOwnerName());
        formatter.format(accountAmountFormat,
                "Amount available",
                initialAmountFrom,
                trDone.getFromAccount().getAvailableAmount(),
                initialAmountTo,
                trDone.getToAccount().getAvailableAmount());
        formatter.format(totalTransferredFormat,
                "Total transferred",
                trDone.getAmount());
        formatter.format(transactionUUIDFormat,
                "Transaction UUID",
                trDone.getUUID());
        return sb.toString();
    }
}
