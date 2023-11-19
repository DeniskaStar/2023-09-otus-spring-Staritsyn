package ru.otus.spring.data.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.data.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест JPA-репозитория для работы с жанра")
@Sql({"classpath:sql/data.sql"})
@ActiveProfiles("test")
@DataJpaTest
class JpaGenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("должен вернуть список жанров по id, если они существуют")
    @Test
    void findByIds_shouldReturnAllGenres_whenGenresExists() {
        List<Long> expectedIds = List.of(1L, 5L);

        List<Genre> actualGenres = genreRepository.findByIdIn(List.of(1L, 5L));

        assertThat(actualGenres).hasSize(2)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> expectedIds.contains(it.getId()));
    }

    @DisplayName("должен вернуть список жанров по id, если какой-либо существует")
    @Test
    void findByIds_shouldReturnAnyGenres_whenAnyGenresExists() {
        List<Long> expectedIds = List.of(3L);

        List<Genre> actualGenres = genreRepository.findByIdIn(List.of(3L, 10L));

        assertThat(actualGenres).hasSize(1);
        assertThat(actualGenres)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> expectedIds.contains(it.getId()));
    }

    @DisplayName("должен вернуть пустой список жанров, если их не существует")
    @Test
    void findByIds_shouldReturnEmptyList_whenGenresNotExists() {
        List<Genre> actualGenres = genreRepository.findByIdIn(List.of(8L, 9L));

        assertThat(actualGenres).isEmpty();
    }
}
