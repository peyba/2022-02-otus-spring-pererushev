package ru.otus.homework02.service.write;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.service.QuestionService;

@RequiredArgsConstructor
public class SimpleWriteQuestionService implements WriteQuestionService {

    private static final String QUESTION_INTRO = "Question: ";

    @Getter
    private final WriteService<Answer> answerWriteService;
    @Getter
    private final QuestionService questionService;

    @Override
    public String write(Question question) {
        return System.lineSeparator() +
                QUESTION_INTRO +
                question.getText() +
                System.lineSeparator() +
                (questionService.hasAnswers(question) ? writeAnswers(question) : "");
    }

    protected String writeAnswers(Question question) {
        var answersText = new StringBuilder();
        var answers = question.getAnswers();

        if (answers.size() == 1) {
            return "";
        }

        for (int i = 0; i < answers.size(); i++) {
            answersText
                    .append(i + 1)
                    .append(") ")
                    .append(answerWriteService.write(answers.get(i)))
                    .append(System.lineSeparator());
        }

        return answersText.toString();
    }
}