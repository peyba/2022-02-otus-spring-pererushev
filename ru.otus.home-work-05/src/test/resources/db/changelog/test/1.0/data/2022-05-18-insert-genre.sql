--liquibase formatted sql
--changeset a.pererushev:hw-05

INSERT INTO public.genre (id, code, name_eng, name_rus)
VALUES
    (1,     'CRIME',           null,        null),
    (2,     'DETECTIVE',       null,        null);

alter sequence public.genre_sequence_id restart with 3;