package ru.otus.homework02.domain;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class Answer {
    @NonNull private final String text;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Answer)) {
            return false;
        }

        return ((Answer) obj).text.equals(text);
    }
}
