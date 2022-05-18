package ru.otus.homework02.service;

import lombok.RequiredArgsConstructor;
import ru.otus.homework02.dao.QuestionDao;
import ru.otus.homework02.domain.*;
import ru.otus.homework02.service.write.WriteQuestionService;

@RequiredArgsConstructor
public class StudentTestingServiceImpl implements StudentTestingService {

    private static final String NOT_NUMERIC_ERROR = "Not numeric answer! Choose one of the suggested answers:";
    private static final String OUT_OF_INDEX_ERROR = "Not answer with what number! Choose one of the suggested answers:";
    private static final String NOT_ANSWER_ERROR = "You dont type any answer! Please try again:";

    private final QuestionDao questionDao;
    private final IOService ioService;
    private final WriteQuestionService writeQuestionService;

    @Override
    public TestResult startTest(Student student, int successPercent) {
        ioService.writeLine("Start testing. Please, answer the questions:");
        var testResult = new TestResult(student, successPercent);

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

            if (question.getQuestionType() == QuestionType.NUM_ANSWER) {
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
