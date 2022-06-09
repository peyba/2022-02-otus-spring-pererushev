package ru.otus.homework02.service;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleIOService implements IOService {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }

    @Override
    public void writeError(String error) {
        System.err.println(error);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
