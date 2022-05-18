package ru.otus.homework02.service.write;

import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.QuestionType;

public class WriteQuestionForQuizService extends SimpleWriteQuestionService {

    private static final String CHOOSE_ANSWER_STRING = "Choose one of the suggested answers: ";
    private static final String TYPE_ANSWER_STRING = "Type your answer: ";

    public WriteQuestionForQuizService(WriteAnswerService writeAnswerService) {
        super(writeAnswerService);
    }

    @Override
    protected String writeAnswers(Question question) {
        return getAskString(question) +
                super.writeAnswers(question);
    }

    private String getAskString (Question question) {
        return question.getQuestionType() == QuestionType.NUM_ANSWER ?
                CHOOSE_ANSWER_STRING + System.lineSeparator() :
                TYPE_ANSWER_STRING;
    }
}
