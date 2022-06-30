--liquibase formatted sql
--changeset a.pererushev:hw-05

CREATE SEQUENCE IF NOT EXISTS public.author_sequence_id;

CREATE TABLE IF NOT EXISTS public.author (
    id              int     default nextval('public.author_sequence_id')    not null,
    first_name      varchar(255)                                            not null,
    second_name     varchar(255)                                            not null,

    CONSTRAINT author_pk PRIMARY KEY (id),
    CONSTRAINT author_first_name_second_name_uc UNIQUE (first_name, second_name)
)