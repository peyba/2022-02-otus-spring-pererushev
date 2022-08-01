package ru.otus.homework08.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework08.domain.sequence.SequenceGeneratorService;

@RequiredArgsConstructor
@Component
public class BookCommentModelListener extends AbstractMongoEventListener<BookComment> {

    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BookComment> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(BookComment.SEQUENCE_NAME));
        }
    }
}