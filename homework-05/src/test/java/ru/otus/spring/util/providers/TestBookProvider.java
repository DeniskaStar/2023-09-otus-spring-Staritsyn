package ru.otus.spring.util.providers;

import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Genre;

import java.util.List;

public class TestBookProvider {

    public static List<Book> getAllBooks() {
        return List.of(
                new Book(1L, "BookTitle_1", new Author(1L, "Author_1"),
                        List.of(new Genre(1L, "Genre_1"), new Genre(2L, "Genre_2"))),
                new Book(1L, "BookTitle_1", new Author(2L, "Author_2"),
                        List.of(new Genre(3L, "Genre_3"), new Genre(4L, "Genre_4"))),
                new Book(1L, "BookTitle_1", new Author(3L, "Author_2"),
                        List.of(new Genre(5L, "Genre_5"), new Genre(6L, "Genre_6")))
        );
    }

    public static Book getOneBook() {
        return new Book(1L, "BookTitle_1", new Author(1L, "Author_1"),
                List.of(new Genre(1L, "Genre_1"), new Genre(2L, "Genre_2")));
    }

    public static Book createOneBookWithoutId() {
        return new Book(null, "BookTitle_new", new Author(1L, "Author_1"),
                List.of(new Genre(1L, "Genre_1"), new Genre(2L, "Genre_2")));
    }

    public static Book createUpdatedOneBook() {
        return new Book(1L, "BookTitle_2_test", new Author(1L, "Author_1"),
                List.of(new Genre(1L, "Genre_1"), new Genre(2L, "Genre_2")));
    }
}
