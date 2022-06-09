--liquibase formatted sql
--changeset a.pererushev:hw-05

INSERT INTO public.book_authors (book_id, author_id)
VALUES
    (1, 1),
    (1, 2)