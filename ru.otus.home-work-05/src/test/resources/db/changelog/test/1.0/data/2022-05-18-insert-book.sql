--liquibase formatted sql
--changeset a.pererushev:hw-05

INSERT INTO public.book (id, name, genre_id)
VALUES
    (1, 'book1',   1),
    (2, 'book2',   1);

alter sequence public.book_sequence_id restart with 3;