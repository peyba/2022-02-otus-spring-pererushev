package ru.otus.homework02.service;

public interface IOService {
    void writeLine(String line);
    void writeError(String error);
    String readLine();
}
