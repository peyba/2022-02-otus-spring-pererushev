package ru.otus.homework02.service;

public abstract class ServiceException extends RuntimeException{

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
