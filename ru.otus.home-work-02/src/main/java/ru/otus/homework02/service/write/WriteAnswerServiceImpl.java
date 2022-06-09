package ru.otus.homework02.service.write;

import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.Answer;

@Component
public class WriteAnswerServiceImpl implements WriteAnswerService {
    @Override
    public String write(Answer answer) {
        return answer.getText();
    }
}
