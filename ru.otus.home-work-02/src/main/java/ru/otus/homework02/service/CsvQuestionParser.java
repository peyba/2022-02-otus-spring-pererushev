package ru.otus.homework02.service;

import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class CsvQuestionParser implements QuestionParser {

    private final static char CORRECT_ANSWER_MARKER = '*';

    @Override
    public Question parse(List<String> fields) {
        var question = fields.get(0);
        if (question.isEmpty()) {
            throw new IncorrectCsvQuestionFileFormatException("Empty row");
        }

        List<Answer> answers = new ArrayList<>();
        for(int i = 1; i < fields.size(); i++){
            if (!fields.get(i).isEmpty()) {
                var answer = parseAnswer(fields.get(i));
                answers.add(answer);
            }
        }

        if (answers.size() == 0) {
            return new Question(question);
        }
        else {
            return new Question(question, answers, correctAnswerIndex);
        }
    }

    private Answer parseAnswer(String answer) {
        return new Answer(
                answer.substring(1),
                isCorrectAnswer(answer)
        );
    }

    private boolean isCorrectAnswer(String answer) {
        return answer.toCharArray()[0] == CORRECT_ANSWER_MARKER;
    }
}
