package ru.otus.homework02.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private static final String NOT_CORRECT_ANSWER_ERROR = "Cannot create question! If count of answers more that one - one of them need be mark as correct.";
    private static final String TOO_MUCH_ANSWERS_ERROR = "Cannot create question! To much correct answers. Question can have only one answer.";

    @Override
    public QuestionType getQuestionType(Question question) {
        if (question.getAnswers().isEmpty()) {
            return QuestionType.FREE_ANSWER;
        }
        else if (question.getAnswers().size() == 1) {
            return QuestionType.STRING_ANSWER;
        }
        else {
            return QuestionType.NUM_ANSWER;
        }
    }

    @Override
    public Answer getCorrectAnswer(Question question) {
        if (getQuestionType(question) == QuestionType.STRING_ANSWER) {
            return question.getAnswers().stream().findFirst().orElse(null);
        }
        else {
            for (var answer : question.getAnswers()) {
                if (answer instanceof CorrectAnswer) {
                    return answer;
                }
            }
        }
        return null;
    }

    @Override
    public Question createQuestion(@NonNull String text, List<Answer> answers) {
        return new CreatableQuestion(text, answers);
    }

    @Override
    public boolean hasAnswers(Question question) {
        return question.getAnswers().size() > 0;
    }

    private void checkQuestion(Question question) {
        if (hasAnswers(question) &&
                question.getAnswers().size() > 1 &&
                getCorrectAnswer(question) == null)
        {
            throw new CreatingQuestionException(NOT_CORRECT_ANSWER_ERROR);
        }

        var correctAnswersCount = 0;
        for(var answers : question.getAnswers()) {
            if (answers instanceof CorrectAnswer) {
                correctAnswersCount++;
            }
        }
        if (correctAnswersCount > 1) {
            throw new CreatingQuestionException(TOO_MUCH_ANSWERS_ERROR);
        }
    }

    private static class CreatableQuestion extends Question {
        public CreatableQuestion(String text, List<Answer> answers) {
            super(text, answers);
        }
    }
}
