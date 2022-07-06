--liquibase formatted sql
--changeset a.pererushev:hw-05

INSERT INTO public.book (name, genre_id)
VALUES
    ('Обитаемый остров',            5),
    ('Новейшая история России',     4)

