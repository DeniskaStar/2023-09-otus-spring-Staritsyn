package ru.otus.spring.util.providers;

import ru.otus.spring.data.domain.Author;

import java.util.List;

public class TestAuthorProvider {

    public static List<Author> getAllAuthors() {
        return List.of(
                new Author(1L, "Author_1"),
                new Author(2L, "Author_2"),
                new Author(3L, "Author_3"));
    }

    public static Author getOneAuthor() {
        return new Author(1L, "Author_1");
    }

    public static Author createOneAuthorWithoutId() {
        return new Author(null, "Author_new");
    }
}
