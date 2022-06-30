package ru.otus.homework08.decorators;

public interface EntityListDecorator<T> {
    String decorate(Iterable<T> entities);
}