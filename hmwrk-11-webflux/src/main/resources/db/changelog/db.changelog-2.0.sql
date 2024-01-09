--liquibase formatted sql

--changeset denstar:01
INSERT INTO authors (id, full_name)
values (1, 'Author_1'), (2, 'Author_2'), (3, 'Author_3');
SELECT SETVAL('authors_id_seq', (SELECT MAX(id) FROM authors));

INSERT INTO genres (id, name)
values (1, 'Genre_1'), (2, 'Genre_2'), (3, 'Genre_3'),
       (4, 'Genre_4'), (5, 'Genre_5'), (6, 'Genre_6');
SELECT SETVAL('genres_id_seq', (SELECT MAX(id) FROM genres));

INSERT INTO books (id, title, author_id)
values (1, 'BookTitle_1', 1), (2, 'BookTitle_2', 2), (3, 'BookTitle_3', 3);
SELECT SETVAL('books_id_seq', (SELECT MAX(id) FROM books));

INSERT INTO books_genres(id, book_id, genre_id)
values (1, 1, 1),   (2, 1, 2),
       (3, 2, 3),   (4, 2, 4),
       (5, 3, 5),   (6, 3, 6);
SELECT SETVAL('books_genres_id_seq', (SELECT MAX(id) FROM books_genres));
