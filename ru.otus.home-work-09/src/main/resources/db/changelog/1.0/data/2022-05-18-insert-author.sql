--liquibase formatted sql
--changeset a.pererushev:hw-05

INSERT INTO public.author (id, first_name, second_name)
VALUES
    (0, 'UNKNOWN',      'UNKNOWN'),
    (1, 'Аркадий',     'Стругацкий'),
    (2, 'Борис',       'Стругацкий')
