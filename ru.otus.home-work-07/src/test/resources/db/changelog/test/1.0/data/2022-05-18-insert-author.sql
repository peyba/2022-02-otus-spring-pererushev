--liquibase formatted sql
--changeset a.pererushev:hw-07

INSERT INTO public.author (id, first_name, second_name)
VALUES
    (1, 'Author1',      'Author1'),
    (2, 'Author2',      'Author2');

alter sequence public.author_sequence_id restart with 3;