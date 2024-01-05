--liquibase formatted sql

--changeset denstar:01
INSERT INTO authors (id, full_name)
values (1, 'Author_1'), (2, 'Author_2'), (3, 'Author_3');
ALTER TABLE authors ALTER COLUMN id RESTART WITH 4;

INSERT INTO genres (id, name)
values (1, 'Genre_1'), (2, 'Genre_2'), (3, 'Genre_3'),
       (4, 'Genre_4'), (5, 'Genre_5'), (6, 'Genre_6');
ALTER TABLE genres ALTER COLUMN id RESTART WITH 7;

INSERT INTO books (id, title, author_id)
values (1, 'BookTitle_1', 1), (2, 'BookTitle_2', 2), (3, 'BookTitle_3', 3);
ALTER TABLE books ALTER COLUMN id RESTART WITH 4;

INSERT INTO books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);

INSERT INTO comments (id, text, book_id)
values (1, 'Comment_1_book_1', 1), (2, 'Comment_2_book_1', 1), (3, 'Comment_3_book_1', 1),
       (4, 'Comment_1_book_2', 2), (5, 'Comment_2_book_2', 2);
ALTER TABLE comments ALTER COLUMN id RESTART WITH 6;
