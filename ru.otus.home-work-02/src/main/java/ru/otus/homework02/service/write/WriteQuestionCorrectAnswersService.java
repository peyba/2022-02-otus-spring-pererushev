package ru.otus.homework02.service.write;

import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.service.QuestionService;

@Component
public class WriteQuestionCorrectAnswersService extends SimpleWriteQuestionService {

    private static final String CORRECT_ANSWER = "Correct answer is: ";

    public WriteQuestionCorrectAnswersService(WriteService<Answer> answerWriteService, QuestionService questionService) {
        super(answerWriteService, questionService);
    }

    @Override
    protected String writeAnswers(Question question) {
        return CORRECT_ANSWER +
                getAnswerWriteService().write(getQuestionService().getCorrectAnswer(question)) +
                System.lineSeparator();
    }
}