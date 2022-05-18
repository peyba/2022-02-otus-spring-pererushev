package ru.otus.homework02.service.write;

import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;

public class WriteQuestionCorrectAnswersService extends SimpleWriteQuestionService {

    private static final String CORRECT_ANSWER = "Correct answer is: ";

    private final WriteService<Answer> answerWriteService;

    public WriteQuestionCorrectAnswersService(WriteService<Answer> answerWriteService) {
        super(answerWriteService);
        this.answerWriteService = answerWriteService;
    }

    @Override
    protected String writeAnswers(Question question) {
        return CORRECT_ANSWER +
                answerWriteService.write(question.getCorrectAnswer()) +
                System.lineSeparator();
    }
}