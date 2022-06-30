package ru.otus.homework08.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

@Accessors(chain = true)
@Document("books")
@Getter
@Setter
public class Book {

    @Id
    private BigInteger id;

    private String name;

    private Genre genre;

    private Set<Author> authors;

    @DBRef(db = "book_comments")
    private Set<BookComment> bookComments;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", authors=" + authors +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(genre, book.genre) && Objects.equals(authors, book.authors) && Objects.equals(bookComments, book.bookComments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, authors, bookComments);
    }
}
