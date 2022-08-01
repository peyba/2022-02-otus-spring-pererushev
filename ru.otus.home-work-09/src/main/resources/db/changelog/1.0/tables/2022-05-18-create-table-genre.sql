--liquibase formatted sql
--changeset a.pererushev:hw-05

CREATE SEQUENCE IF NOT EXISTS public.genre_sequence_id;

CREATE TABLE IF NOT EXISTS public.genre (
    id          int         default nextval('public.genre_sequence_id') not null,
    code        varchar(20)                                             not null,
    name_eng    text,
    name_rus    text,

    CONSTRAINT genre_pk PRIMARY KEY (id),
    CONSTRAINT genre_name_uq UNIQUE (code)
)