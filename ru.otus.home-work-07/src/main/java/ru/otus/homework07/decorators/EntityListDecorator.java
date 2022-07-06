package ru.otus.homework07.decorators;

public interface EntityListDecorator<T> {
    String decorate(Iterable<T> entities);
}