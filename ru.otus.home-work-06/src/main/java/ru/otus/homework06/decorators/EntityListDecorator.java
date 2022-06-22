package ru.otus.homework06.decorators;

public interface EntityListDecorator<T> {
    String decorate(Iterable<T> entities);
}