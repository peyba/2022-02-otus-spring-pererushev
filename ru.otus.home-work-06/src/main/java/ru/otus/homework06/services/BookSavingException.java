package ru.otus.homework06.services;

public class BookSavingException extends RuntimeException {
    public BookSavingException (String message) {
        super(message);
    }
}
