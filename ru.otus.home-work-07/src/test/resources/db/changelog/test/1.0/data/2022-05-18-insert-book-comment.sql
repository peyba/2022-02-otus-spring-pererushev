--liquibase formatted sql
--changeset a.pererushev:hw-07

INSERT INTO public.book_comment (id, book_id, text)
VALUES
    (1, 1,  'Good, but shor'),
    (2, 1,  'Out of stock'),
    (3, 2,  'Out of stock'),
    (4, 2,  'Very boooooriiiiing!!!'),
    (5, 2,  'Got it.');


alter sequence public.book_comment_sequence_id restart with 6;