package ru.otus.homework08.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.homework08.domain.sequence.SequenceGeneratorService;

@RequiredArgsConstructor
@Component
public class BookModelListener extends AbstractMongoEventListener<Book> {

    private final SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(Book.SEQUENCE_NAME));
        }
    }
}