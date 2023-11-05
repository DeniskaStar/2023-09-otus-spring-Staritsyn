--liquibase formatted sql

--changeset denstar:01
CREATE TABLE IF NOT EXISTS authors
(
    id bigserial primary key,
    full_name varchar(255)
);

--changeset denstar:02
CREATE TABLE IF NOT EXISTS genres
(
    id bigserial primary key,
    name varchar(255)
);

--changeset denstar:03
CREATE TABLE IF NOT EXISTS books
(
    id bigserial primary key,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade
);

--changeset denstar:04
CREATE TABLE IF NOT EXISTS books_genres
(
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);
