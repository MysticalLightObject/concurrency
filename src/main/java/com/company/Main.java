package com.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final Path pathToAccounts = Paths.get("/home/mike/IdeaProjects/ConcurrecyPractice/src/main/resources/accounts/");
    public static AtomicInteger counter = new AtomicInteger(50);
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static int threadCount = 8;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        logger.info("Starting application with " + threadCount + " threads");
        AccountManager am = new AccountManager();
        ArrayList<Account> accountsList = am.getAccounts();
        ExecutorService es = Executors.newFixedThreadPool(threadCount);
        while (counter.get() > 0) {
            es.execute(new TransactionManagerTask(accountsList));
        }

        es.shutdown();

        try {
            es.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long finish = System.currentTimeMillis();
        logger.info("Time elapsed: " + (finish - start) + " milliseconds");
    }


}

