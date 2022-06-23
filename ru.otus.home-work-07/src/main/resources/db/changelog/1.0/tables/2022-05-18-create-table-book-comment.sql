--liquibase formatted sql
--changeset a.pererushev:hw-05

CREATE SEQUENCE IF NOT EXISTS public.book_comment_sequence_id;

CREATE TABLE IF NOT EXISTS public.book_comment (
    id              int     default nextval('public.book_comment_sequence_id')  not null,
    book_id         int                                                         not null,
    text            text                                                        not null,

    CONSTRAINT book_comment_pk PRIMARY KEY (id),
    CONSTRAINT book_comment_book_fk FOREIGN KEY (book_id) REFERENCES public.book(id)
)