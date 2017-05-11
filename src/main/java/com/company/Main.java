package com.company;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final Path pathToAccounts = Paths.get("/home/mike/IdeaProjects/ConcurrecyPractice/src/main/resources/accounts/");
    public static AtomicInteger counter = new AtomicInteger(50);

    public static void main(String[] args) {
        AccountManager am = new AccountManager();
        ArrayList<Account> accountsList = am.getAccounts();
        ExecutorService es = Executors.newFixedThreadPool(2);
        for (int i = counter.get(); i > 0; i--) {
            es.execute(new TransactionManagerTask(accountsList));
        }
        es.shutdown();
    }


}

