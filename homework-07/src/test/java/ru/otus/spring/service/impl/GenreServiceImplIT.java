package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.genre.GenreCreateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.dto.genre.GenreUpdateDto;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест сервиса для работы с жанрами")
@Sql({"classpath:sql/data.sql"})
@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class GenreServiceImplIT {

    @Autowired
    private GenreService genreService;

    @DisplayName("должен вернуть список жанров")
    @Test
    void findAll_shouldReturnAllGenres() {
        var actualGenres = genreService.findAll();

        assertThat(actualGenres).hasSize(6)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getName()));
    }

    @DisplayName("должен найти жанр по id")
    @Test
    void findById_shouldReturnGenre_whenExists() {
        Optional<GenreDto> actualGenre = genreService.findById(1L);

        assertThat(actualGenre).isPresent().get()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Genre_1");
    }

    @DisplayName("должен вернуть пустой элемент, если жанра по id не существует")
    @Test
    void findById_shouldReturnEmptyGenre_whenNotExists() {
        Optional<GenreDto> actualGenre = genreService.findById(10L);

        assertThat(actualGenre).isEmpty();
    }

    @DisplayName("должен вернуть список жанров по id, если они существуют")
    @Test
    void findByIds_shouldReturnAllGenres_whenGenresExists() {
        List<Long> expectedIds = List.of(1L, 5L);

        List<GenreDto> actualGenres = genreService.findByIds(List.of(1L, 5L));

        assertThat(actualGenres).hasSize(2)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> expectedIds.contains(it.getId()));
    }

    @DisplayName("должен вернуть список жанров по id, если какой-либо существует")
    @Test
    void findByIds_shouldReturnAnyGenres_whenAnyGenresExists() {
        List<Long> expectedIds = List.of(3L);

        List<GenreDto> actualGenres = genreService.findByIds(List.of(3L, 10L));

        assertThat(actualGenres).hasSize(1);
        assertThat(actualGenres)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> expectedIds.contains(it.getId()));
    }

    @DisplayName("должен вернуть пустой список жанров, если их не существует")
    @Test
    void findByIds_shouldReturnEmptyList_whenGenresNotExists() {
        List<GenreDto> actualGenres = genreService.findByIds(List.of(8L, 9L));

        assertThat(actualGenres).isEmpty();
    }

    @DisplayName("должен сохранить жанр")
    @Test
    void create() {
        var createGenreRequest = new GenreCreateDto("Genre_test");

        var expectedGenre = genreService.create(createGenreRequest);
        var actualGenres = genreService.findById(expectedGenre.getId());

        assertThat(actualGenres).isPresent().get()
                .isEqualTo(expectedGenre);
    }

    @DisplayName("должен обновить жанр")
    @Test
    void update() {
        GenreUpdateDto updateGenreRequest = new GenreUpdateDto(1L, "Updated Genre");

        var updatedGenre = genreService.update(updateGenreRequest);

        assertThat(updatedGenre).isNotNull();
        assertThat(updatedGenre.getId()).isEqualTo(1L);
        assertThat(updatedGenre.getName()).isEqualTo("Updated Genre");
    }

    @DisplayName("должен удалить жанр по id")
    @Test
    void deleteBookById() {
        genreService.deleteById(1L);

        var actualGenre = genreService.findById(1L);

        assertThat(actualGenre).isEmpty();
    }
}
