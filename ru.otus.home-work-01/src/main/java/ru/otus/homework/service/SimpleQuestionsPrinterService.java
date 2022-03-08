package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.entity.Question;

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
