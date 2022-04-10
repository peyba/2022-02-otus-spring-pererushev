package ru.otus.homework02.domain;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TestResult {
    private final Student student;
    private final Map<Question, String> answers;

    public TestResult(Student student) {
        this.student = student;
        this.answers = new HashMap<>();
    }

    public void addAnswer(Question question, String answer) {
        answers.put(question, answer);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append("Test result of student ")
                .append(student)
                .append('\n');

        for (var question : answers.keySet()) {
            sb.append("Question is: ")
                    .append(question.getText())
                    .append('\n')
                    .append("your answer is: ");

            if (question.getQuestionType() == QuestionType.NUM_ANSWER) {
                int answerIndex = Integer.parseInt(answers.get(question)) - 1;
                sb.append(answerIndex)
                        .append(") "); // TODO: очень часто приходится это делать. нужно поместить эту логику в класс Question
                if (question.getAnswers().size() > answerIndex) {
                    sb.append(question.getAnswers().get(answerIndex));
                }
                else {
                    sb.append("...");
                }
            }
            else {
                sb.append(answers.get(question));
            }

            if (question.hasCorrectAnswer()) {
                sb.append('\n')
                        .append("correct answer is: ")
                        .append(question.getCorrectAnswer());
            }

            sb.append("\n\n");
        }

        return sb.toString();
    }
}
