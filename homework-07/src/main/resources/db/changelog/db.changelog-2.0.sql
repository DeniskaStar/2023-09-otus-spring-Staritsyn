--liquibase formatted sql

--changeset denstar:01
CREATE TABLE IF NOT EXISTS comments
(
    id bigserial primary key,
    text varchar(1024),
    book_id bigint references books (id) on delete cascade not null
)
