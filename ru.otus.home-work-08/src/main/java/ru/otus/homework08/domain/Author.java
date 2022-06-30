package ru.otus.homework08.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "author", schema = "public")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence_id")
    @SequenceGenerator(name = "author_sequence_id", allocationSize = 1, sequenceName = "public.author_sequence_id")
    @Column(name = "Id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;
}
