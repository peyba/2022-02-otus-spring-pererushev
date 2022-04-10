package ru.otus.homework02.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Question {

    @Getter
    private final String text;
    private final List<Answer> answers;
    @Getter
    private final QuestionType questionType;

    private Integer correctAnswerIndex;

    public Question(String text) {
        this(text, new ArrayList<>());
    }

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;

        if (answers.size() == 0 ||
                correctAnswerIndex == -1) {
            questionType = QuestionType.FREE_ANSWER;
        }
        else if (answers.size() == 1) {
            questionType = QuestionType.STRING_ANSWER;
        }
        else {
            questionType = QuestionType.NUM_ANSWER;
        }
    }

    public boolean hasAnswers() {
        return answers.size() != 0;
    }

    // TODO: для всех преобразований вопросов в текст создать сервисы
    public String getTextWithAnswers() {
        var questionString = new StringBuilder();
        questionString
                .append(text)
                .append(System.lineSeparator());

        if (questionType == QuestionType.NUM_ANSWER) {
            // Do string like "1) answer_1; 2) answer_2; ...etc"
            for (int i = 1; i <= answers.size(); i++) {
                questionString
                        .append(i)
                        .append(") ")
                        .append(answers.get(i - 1).getText())
                        .append(System.lineSeparator());
            }

        }

        return questionString.toString();
    }

    // TODO: для всех преобразований вопросов в текст создать сервисы
    public String ask() {
        var questionString = new StringBuilder();
        questionString.append(getTextWithAnswers())
                .append(
                        questionType == QuestionType.NUM_ANSWER ?
                                "Choose one of the suggested answers:" : "Type your answer:"
                )
                .append(System.lineSeparator());
        return questionString.toString();
    }

    public Answer getCorrectAnswer() {
        return !hasCorrectAnswer() ?
                answers.get(getCorrectAnswerIndex()) :
                null;
    }

    // TODO: для всех преобразований вопросов в текст создать сервисы
    public String getCorrectAnswerText() {
        var answer = getCorrectAnswer();

        if (questionType == QuestionType.NUM_ANSWER) {
            return correctAnswerIndex.toString() +
                    ") " +
                    answers.get(correctAnswerIndex);
        }
        else {
            return answers.get(correctAnswerIndex);
        }
    }

    public boolean hasCorrectAnswer() {
        return getCorrectAnswerIndex() != -1;
    }

    private int getCorrectAnswerIndex() {
        if (correctAnswerIndex == null) {
            correctAnswerIndex = -1;
            for (int i = 0; i < answers.size(); i++) {
                if (answers.get(i).isCorrect()) {
                    correctAnswerIndex = i;
                    break;
                }
            }
        }
        return correctAnswerIndex;
    }
}