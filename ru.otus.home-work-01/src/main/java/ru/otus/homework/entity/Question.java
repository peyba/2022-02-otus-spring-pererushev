package ru.otus.homework.entity;

import java.util.List;

public interface Question {
    String getText();
    boolean hasAnswers();
    List<String> getAnswers();
}
