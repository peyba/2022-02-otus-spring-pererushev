package ru.otus.homework07.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Objects;

@Accessors(chain = true)
@Entity
@Table(name = "book_comment", schema = "public")
@Getter
@Setter
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_comment_sequence_id")
    @SequenceGenerator(name = "book_comment_sequence_id", sequenceName = "public.book_comment_sequence_id", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Book book;

    @Column(name = "text")
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookComment that = (BookComment) o;
        return Objects.equals(id, that.id) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}