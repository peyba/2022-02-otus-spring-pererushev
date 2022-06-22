--liquibase formatted sql
--changeset a.pererushev:hw-06

INSERT INTO public.book_comment (book_id, text)
VALUES
    (1,  'Good, but short'),
    (1,  'Out of stock'),
    (2,  'Out of stock'),
    (2,  'Very boooooriiiiing!!!'),
    (2,  'Got it.');


alter sequence public.book_comment_sequence_id restart with 6;