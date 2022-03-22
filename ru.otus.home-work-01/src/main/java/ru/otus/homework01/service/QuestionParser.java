package ru.otus.homework01.service;

import ru.otus.homework01.domain.Question;

import java.util.List;

public interface QuestionParser {
    Question parse(List<String> row);
}
