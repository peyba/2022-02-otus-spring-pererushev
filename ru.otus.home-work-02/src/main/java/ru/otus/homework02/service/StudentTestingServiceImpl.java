package ru.otus.homework02.service;

import ru.otus.homework02.dao.QuestionDao;
import ru.otus.homework02.domain.QuestionType;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;

import java.util.Scanner;

public class StudentTestingServiceImpl implements StudentTestingService {

    private final QuestionDao questionDao;

    public StudentTestingServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public TestResult startTest(Student student) {
        System.out.println("Start testing. Please, answer the questions:");
        var testResult = new TestResult(student);

        var scanner = new Scanner(System.in);

        var questions =  questionDao.getAll();

        if (questions == null || questions.size() == 0) {
            System.out.println("Error! No questions found.");
            return null;
        }

        for(var question : questions) {
            System.out.print(question.ask());

            var answer = "";
            while (answer.isEmpty()) {
                answer = scanner.nextLine();

                if (question.getQuestionType() == QuestionType.NUM_ANSWER) {
                    try {
                        Integer.parseInt(answer);
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Not numeric answer! Choose one of the suggested answers:");
                        answer = "";
                    }
                }
            }
            testResult.addAnswer(question, answer);
        }

        return testResult;
    }
}
