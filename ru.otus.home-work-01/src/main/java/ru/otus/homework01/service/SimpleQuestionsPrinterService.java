package ru.otus.homework01.service;

import ru.otus.homework01.dao.QuestionDao;
import ru.otus.homework01.domain.Question;

public class SimpleQuestionsPrinterService implements QuestionsPrinterService {

    private final QuestionDao questionDao;

    public SimpleQuestionsPrinterService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void print() {
        var questions = questionDao.getAll();
        for (Question question : questions) {
            System.out.println(question);
        }
    }
}
