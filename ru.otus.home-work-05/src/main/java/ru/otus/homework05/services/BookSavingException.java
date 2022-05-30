package ru.otus.homework05.services;

import java.util.function.Supplier;

public class BookSavingException extends RuntimeException {
    public BookSavingException (String message) {
        super(message);
    }
}
