--liquibase formatted sql
--changeset a.pererushev:hw-05

CREATE SEQUENCE IF NOT EXISTS public.book_authors_sequence_id;

CREATE TABLE IF NOT EXISTS public.book_authors (
    id          int     default nextval('public.book_authors_sequence_id')  not null,
    book_id     int                                                         not null,
    author_id   int                                                         not null,

    CONSTRAINT book_authors_pk PRIMARY KEY (id),
    CONSTRAINT book_authors_uq UNIQUE (book_id, author_id),
    CONSTRAINT book_authors_book_id_fk FOREIGN KEY (book_id) REFERENCES public.book(id),
    CONSTRAINT book_authors_author_id_fk FOREIGN KEY (author_id) REFERENCES public.author(id)
)