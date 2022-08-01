--liquibase formatted sql
--changeset a.pererushev:hw-05

CREATE SEQUENCE IF NOT EXISTS public.book_sequence_id;

CREATE TABLE IF NOT EXISTS public.book (
    id          int     default nextval('public.book_sequence_id')  not null,
    name        text                                                not null,
    genre_id    int                                                 not null,

    CONSTRAINT book_pk PRIMARY KEY (id),
    CONSTRAINT book_genre_fk FOREIGN KEY (genre_id) REFERENCES public.genre(id)
)