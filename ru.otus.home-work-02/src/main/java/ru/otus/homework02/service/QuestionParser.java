package ru.otus.homework02.service;

import ru.otus.homework02.domain.Question;

import java.util.List;

public interface QuestionParser {
    Question parse(List<String> row);
}
