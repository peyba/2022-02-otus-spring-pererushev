package ru.otus.homework02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CsvQuestionParser implements QuestionParser {

    private final static char CORRECT_ANSWER_MARKER = '*';
    private final static char CORRECT_ANSWER_MARKER_INDEX = 0;
    private final static int QUESTION_FIELD_INDEX = 0;
    private final static int ANSWERS_START_INDEX = 1;

    private final QuestionService questionService;
    private final AnswerService answerService;

    @Override
    public Question parse(List<String> fields) {
        var questionText = fields.get(QUESTION_FIELD_INDEX);
        if (questionText.isEmpty()) {
            throw new IncorrectCsvQuestionFileFormatException("Empty row");
        }

        List<Answer> answers = new ArrayList<>();
        for(int i = ANSWERS_START_INDEX; i < fields.size(); i++){
            if (fields.get(i).isEmpty()) {
                break;
            }

            answers.add(answerService.createAnswer(getAnswerText(fields.get(i)), isCorrectAnswer(fields.get(i))));
        }

        return questionService.createQuestion(questionText, answers);
    }

    private String getAnswerText(String text) {
        return isCorrectAnswer(text) ?
                text.substring(ANSWERS_START_INDEX) :
                text;
    }

    private boolean isCorrectAnswer(String text) {
        return text.charAt(CORRECT_ANSWER_MARKER_INDEX) == CORRECT_ANSWER_MARKER;
    }
}
