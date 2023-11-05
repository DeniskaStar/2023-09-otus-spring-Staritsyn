package ru.otus.spring.util.providers;

import ru.otus.spring.data.domain.Genre;

import java.util.List;

public class TestGenreProvider {

    public static List<Genre> createAllGenres() {
        return List.of(
                new Genre(1L, "Genre_1"),
                new Genre(2L, "Genre_2"),
                new Genre(3L, "Genre_3"),
                new Genre(4L, "Genre_4"),
                new Genre(5L, "Genre_5"),
                new Genre(6L, "Genre_6"));
    }

    public static Genre createOneGenreWithoutId() {
        return new Genre(null, "Genre_new");
    }
}
