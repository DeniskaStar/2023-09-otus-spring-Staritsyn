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

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);
