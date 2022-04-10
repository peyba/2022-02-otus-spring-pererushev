package ru.otus.homework02.service;

public class IncorrectCsvQuestionFileFormatException extends RuntimeException {

    public IncorrectCsvQuestionFileFormatException() {}

    public IncorrectCsvQuestionFileFormatException(String message) {
        super(message);
    }

    public IncorrectCsvQuestionFileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
