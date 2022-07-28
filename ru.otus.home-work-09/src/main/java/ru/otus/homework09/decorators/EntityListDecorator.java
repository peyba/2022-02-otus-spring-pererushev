package ru.otus.homework09.decorators;

public interface EntityListDecorator<T> {
    String decorate(Iterable<T> entities);
}