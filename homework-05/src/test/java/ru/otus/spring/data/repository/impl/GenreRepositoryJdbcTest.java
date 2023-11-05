package ru.otus.spring.data.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.util.providers.TestAuthorProvider;
import ru.otus.spring.util.providers.TestGenreProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест JDBC-репозитория для работы с жанра")
@Sql({"classpath:sql/data.sql"})
@Import(value = {GenreRepositoryJdbc.class})
@JdbcTest
class GenreRepositoryJdbcTest {

    @Autowired
    private GenreRepositoryJdbc genreRepository;

    @DisplayName("должен вернуть список жанров")
    @Test
    void findAll_shouldReturnAllGenres() {
        List<Genre> expectedGenres = TestGenreProvider.createAllGenres();

        var actualGenres = genreRepository.findAll();

        assertThat(actualGenres).hasSize(6);
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @DisplayName("должен вернуть список жанров по id, если они существуют")
    @Test
    void findByIds_shouldReturnAllGenres_whenGenresExists() {
        List<Genre> expectedGenres = List.of(
                new Genre(1L, "Genre_1"),
                new Genre(5L, "Genre_5")
        );

        List<Genre> actualGenres = genreRepository.findByIds(List.of(1L, 5L));

        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @DisplayName("должен вернуть список жанров по id, если какой-либо существует")
    @Test
    void findByIds_shouldReturnAnyGenres_whenAnyGenresExists() {
        List<Genre> expectedGenres = List.of(
                new Genre(3L, "Genre_3"),
                new Genre(10L, "Genre_10")
        );

        List<Genre> actualGenres = genreRepository.findByIds(List.of(6L, 3L, 5L));

        assertThat(actualGenres).hasSize(3);
        assertThat(actualGenres).containsAnyElementsOf(expectedGenres);
    }

    @DisplayName("должен вернуть пустой список жанров, если их не существует")
    @Test
    void findByIds_shouldReturnEmptyList_whenGenresNotExists() {
        List<Genre> actualGenres = genreRepository.findByIds(List.of(8L, 9L));

        assertThat(actualGenres).isEmpty();
    }

    @DisplayName("должен сохранить жанр")
    @Test
    void save() {
        var newGenre = TestGenreProvider.createOneGenreWithoutId();

        var savedGenre = genreRepository.save(newGenre);

        assertThat(savedGenre).isNotNull();
        assertThat(savedGenre).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(newGenre);
    }
}
