package ru.otus.homework02.service;

import ru.otus.homework02.dao.QuestionDao;
import ru.otus.homework02.domain.Question;

public class SimpleQuestionsPrinterService implements QuestionsPrinterService {

    private final QuestionDao questionDao;

    public SimpleQuestionsPrinterService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void print() {
        var questions = questionDao.getAll();

        if (questions == null || questions.size() == 0) {
            System.out.println("Error! No questions found.\n");
            return;
        }

        for (Question question : questions) {
            System.out.println(question.getTextWithAnswers());
        }
    }
}
