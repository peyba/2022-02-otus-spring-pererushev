package ru.otus.homework02.domain;

public class CreatingQuestionException extends RuntimeException {

    public CreatingQuestionException() {
        super();
    }

    public CreatingQuestionException(String message) {
        super(message);
    }
}