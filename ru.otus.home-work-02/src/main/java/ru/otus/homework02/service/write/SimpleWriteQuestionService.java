package ru.otus.homework02.service.write;

import lombok.RequiredArgsConstructor;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;

@RequiredArgsConstructor
public class SimpleWriteQuestionService implements WriteQuestionService {

    private static final String QUESTION_INTRO = "Question: ";

    private final WriteService<Answer> answerWriteService;

    @Override
    public String write(Question question) {
        return System.lineSeparator() +
                QUESTION_INTRO +
                question.getText() +
                System.lineSeparator() +
                (question.hasAnswers() ? writeAnswers(question) : "");
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