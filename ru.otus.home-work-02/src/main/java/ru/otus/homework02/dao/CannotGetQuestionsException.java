package ru.otus.homework02.dao;

public class CannotGetQuestionsException extends RuntimeException{
    public CannotGetQuestionsException(String text) {
        super(text);
    }
}
