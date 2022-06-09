package ru.otus.homework02.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.homework02.dao.QuestionDao;
import ru.otus.homework02.domain.*;
import ru.otus.homework02.service.write.WriteQuestionService;

@Component
public class StudentTestingServiceImpl implements StudentTestingService {

    private static final String NOT_NUMERIC_ERROR = "Not numeric answer! Choose one of the suggested answers:";
    private static final String OUT_OF_INDEX_ERROR = "Not answer with what number! Choose one of the suggested answers:";
    private static final String NOT_ANSWER_ERROR = "You dont type any answer! Please try again:";

    private final QuestionDao questionDao;
    private final IOService ioService;
    private final WriteQuestionService writeQuestionService;
    private final QuestionService getQuestionType;

    public StudentTestingServiceImpl(
            QuestionDao questionDao,
            IOService ioService,
            @Qualifier("writeQuestionForQuizService") WriteQuestionService writeQuestionService,
            QuestionService getQuestionType
    ) {
        this.questionDao = questionDao;
        this.ioService = ioService;
        this.writeQuestionService = writeQuestionService;
        this.getQuestionType = getQuestionType;
    }

    @Override
    public TestResult startTest(Student student) {
        ioService.writeLine("Start testing. Please, answer the questions:");
        var testResult = new TestResult(student);

        var questions =  questionDao.getAll();

        if (questions == null || questions.size() == 0) {
            ioService.writeLine("Error! Questions not found.");
            return null;
        }

        for(var question : questions) {
            ioService.writeLine(writeQuestionService.write(question));
            var answer = getAnswer(question);
            testResult.addAnswer(question, answer);
        }

        return testResult;
    }

    private Answer getAnswer(Question question) {
        String answerText;
        while (true) {
            answerText = ioService.readLine();

            if (answerText.isEmpty()) {
                ioService.writeLine(NOT_ANSWER_ERROR);
                continue;
            }

            if (getQuestionType.getQuestionType(question) == QuestionType.NUM_ANSWER) {
                try {
                    int answerIndex = Integer.parseInt(answerText) - 1;
                    return question.getAnswers().get(answerIndex);
                }
                catch (NumberFormatException e) {
                    ioService.writeLine(NOT_NUMERIC_ERROR);
                } catch (IndexOutOfBoundsException e) {
                    ioService.writeLine(OUT_OF_INDEX_ERROR);
                }
            } else {
                return new Answer(answerText);
            }
        }
    }
}
