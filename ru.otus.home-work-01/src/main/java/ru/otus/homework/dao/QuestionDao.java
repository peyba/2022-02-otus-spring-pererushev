package ru.otus.homework.dao;

import ru.otus.homework.domain.QuestionsCollection;

import java.io.IOException;

public interface QuestionDao {
    QuestionsCollection getAll();
}
