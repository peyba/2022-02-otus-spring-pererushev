package ru.otus.homework06.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Accessors(chain = true)
@Entity
@Table(name = "book", schema = "public")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence_id")
    @SequenceGenerator(name = "book_sequence_id", allocationSize = 1, sequenceName = "public.book_sequence_id")
    @Column(name = "Id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(targetEntity = Genre.class)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @ManyToMany(targetEntity = Author.class, fetch=FetchType.EAGER)
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Fetch(value = FetchMode.SUBSELECT)
    @Column(nullable = false)
    private Set<Author> authors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @Column(nullable = false)
    private Set<BookComment> bookComments;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", authors=" + authors +
                ", bookComments=" + bookComments +
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
