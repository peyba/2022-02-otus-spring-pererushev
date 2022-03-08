package ru.otus.homework.dao;

public class IncorrectCsvQuestionFileFormatException extends Exception {

    public IncorrectCsvQuestionFileFormatException() {}

    public IncorrectCsvQuestionFileFormatException(String message) {
        super(message);
    }

    public IncorrectCsvQuestionFileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
