package com.company;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Main {
    public static final Path pathToAccounts = Paths.get("/home/mike/IdeaProjects/ConcurrecyPractice/src/main/resources/accounts/");
    public static AtomicInteger counter = new AtomicInteger(50);
    private static final Gson gson = new Gson();

    public static void main(String[] args) {

        ArrayList<Account> accountsList = getAccounts();
        accountsList.forEach((account) -> System.out.println("AccountId: " + account.getAccountId() + "\n" + "Account owner name: " + account.getOwnerName() + "\n" + "Available amount: " + account.getAvailableAmount() + "\n"));
        ExecutorService es = Executors.newFixedThreadPool(1);
        for (int i = counter.get(); i > 0; i--) {
            es.execute(new AccountManager());
        }
        es.shutdown();
    }

    private static ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Main.pathToAccounts)) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    String s = readFromFile(filePath);
                    Account account = gson.fromJson(s, Account.class);
                    accounts.add(account);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private static String readFromFile(Path filePath) {
        String contents = "";
        try (InputStream in = Files.newInputStream(filePath);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            while (reader.readLine() != null) {
                byte[] encoded = Files.readAllBytes(filePath);
                contents = new String(encoded, StandardCharsets.UTF_8);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return contents;
    }
}

