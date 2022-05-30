--liquibase formatted sql
--changeset a.pererushev:hw-05

INSERT INTO public.book_authors (id, book_id, author_id)
VALUES
    (1, 1, 1),
    (2, 1, 2);

alter sequence public.book_authors_sequence_id restart with 3;