package ru.otus.homework02.domain;

import lombok.NonNull;

public class QuestionBuilder {
    private static final String NOT_CORRECT_ANSWER_ERROR = "Cannot create question! If count of answers more that one - one of them need be mark as correct.";
    private static final String TOO_MUCH_ANSWERS_ERROR = "Cannot create question! To much correct answers. Question can have only one answer.";

    private final QuestionForBuilder question;

    public QuestionBuilder(@NonNull String text) {
        question = new QuestionForBuilder(text);
    }

    public void addAnswer(@NonNull String text, @NonNull Boolean isCorrect) {
        if (question.getCorrectAnswer() != null &&
                question.getAnswers().size() > 1 &&
                isCorrect
        ) {
            throw new CreatingQuestionException(TOO_MUCH_ANSWERS_ERROR);
        }
        question.addAnswer(text, isCorrect);
    }

    public Question getQuestion() {
        checkQuestion();
        return question;
    }

    private void checkQuestion() {
        if (question.hasAnswers() &&
                question.getAnswers().size() > 1 &&
                question.getCorrectAnswer() == null)
        {
            throw new CreatingQuestionException(NOT_CORRECT_ANSWER_ERROR);
        }
    }

    private static class QuestionForBuilder extends Question {
        public QuestionForBuilder(@NonNull String text) {
            super(text);
        }

        public void addAnswer(@NonNull String text, @NonNull Boolean isCorrect) {
            super.addAnswer(text, isCorrect);
        }
    }
}
