package ru.otus.homework05.decorators;

import java.util.Map;

public interface EntityListDecorator<T> {
    String decorate(Iterable<T> entities);
}