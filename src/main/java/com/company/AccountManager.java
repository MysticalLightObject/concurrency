package com.company;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class AccountManager {
    private final Gson gson = new Gson();

    public ArrayList<Account> getAccounts() {
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

    private String readFromFile(Path filePath) {
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
