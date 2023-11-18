package ru.otus.spring.data.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;
import ru.otus.spring.data.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест JPA-репозитория для работы с жанра")
@Sql({"classpath:sql/data.sql"})
@Import(value = {JpaGenreRepository.class})
@ActiveProfiles("test")
@DataJpaTest
class JpaGenreRepositoryTest {

    @Autowired
    private JpaGenreRepository genreRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("должен вернуть список жанров")
    @Test
    void findAll_shouldReturnAllGenres() {
        var actualGenres = genreRepository.findAll();

        assertThat(actualGenres).hasSize(6)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getName()));
    }

    @DisplayName("должен вернуть список жанров по id, если они существуют")
    @Test
    void findByIds_shouldReturnAllGenres_whenGenresExists() {
        List<Long> expectedIds = List.of(1L, 5L);

        List<Genre> actualGenres = genreRepository.findByIds(List.of(1L, 5L));

        assertThat(actualGenres).hasSize(2)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> expectedIds.contains(it.getId()));
    }

    @DisplayName("должен вернуть список жанров по id, если какой-либо существует")
    @Test
    void findByIds_shouldReturnAnyGenres_whenAnyGenresExists() {
        List<Long> expectedIds = List.of(3L);

        List<Genre> actualGenres = genreRepository.findByIds(List.of(3L, 10L));

        assertThat(actualGenres).hasSize(1);
        assertThat(actualGenres)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> expectedIds.contains(it.getId()));
    }

    @DisplayName("должен вернуть пустой список жанров, если их не существует")
    @Test
    void findByIds_shouldReturnEmptyList_whenGenresNotExists() {
        List<Genre> actualGenres = genreRepository.findByIds(List.of(8L, 9L));

        assertThat(actualGenres).isEmpty();
    }

    @DisplayName("должен сохранить жанр")
    @Test
    void create() {
        var newGenre = new Genre(null, "Genre_test");

        var expectedGenre = genreRepository.save(newGenre);
        var actualGenres = genreRepository.findByIds(List.of(expectedGenre.getId()));

        assertThat(actualGenres).isNotEmpty()
                .containsAnyElementsOf(List.of(expectedGenre));
    }

    @DisplayName("должен обновить жанр")
    @Test
    void update() {
        List<Genre> genres = genreRepository.findByIds(List.of(1L));
        var existsGenre = genres.get(0);
        existsGenre.setName("Genre_test");

        var updatedGenre = genreRepository.save(existsGenre);

        assertThat(updatedGenre).isNotNull();
        assertThat(updatedGenre.getId()).isEqualTo(1L);
        assertThat(updatedGenre.getName()).isEqualTo("Genre_test");
    }

    @DisplayName("должен удалить жанр по id")
    @Test
    void deleteBookById() {
        List<Genre> maybeGenres = genreRepository.findByIds(List.of(1L));
        assertThat(maybeGenres).isNotEmpty();
        testEntityManager.clear();

        genreRepository.deleteById(1);
        List<Genre> deletedGenre = genreRepository.findByIds(List.of(1L));

        assertThat(deletedGenre).isEmpty();
    }
}
