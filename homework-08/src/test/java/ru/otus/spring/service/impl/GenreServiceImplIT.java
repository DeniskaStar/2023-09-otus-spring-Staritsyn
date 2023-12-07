package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.genre.GenreCreateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.dto.genre.GenreUpdateDto;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест сервиса для работы с жанрами")
@ActiveProfiles("test")
@SpringBootTest
public class GenreServiceImplIT {

    @Autowired
    private GenreService genreService;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен вернуть список жанров")
    @Test
    void findAll_shouldReturnAllGenres() {
        List<GenreDto> actualGenres = genreService.findAll();

        assertThat(actualGenres).hasSize(4)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getName()));
    }

    @DisplayName("должен найти жанр по id")
    @Test
    void findById_shouldReturnGenre_whenExists() {
        GenreCreateDto genreCreateRequest = new GenreCreateDto("test genre");
        GenreDto expectedGenre = genreService.create(genreCreateRequest);

        Optional<GenreDto> actualGenre = genreService.findById(expectedGenre.getId());

        assertThat(actualGenre).isPresent().get()
                .hasFieldOrPropertyWithValue("id", expectedGenre.getId())
                .hasFieldOrPropertyWithValue("name", "test genre");
    }

    @DisplayName("должен вернуть пустой элемент, если жанра по id не существует")
    @Test
    void findById_shouldReturnEmptyGenre_whenNotExists() {
        Optional<GenreDto> actualGenre = genreService.findById("dummy");

        assertThat(actualGenre).isEmpty();
    }

    @DisplayName("должен обновить жанр")
    @Test
    void update() {
        GenreDto existsGenre = genreService.findAll().get(0);
        GenreUpdateDto updateGenreRequest = new GenreUpdateDto(existsGenre.getId(), "test genre");

        var updatedGenre = genreService.update(updateGenreRequest);

        assertThat(updatedGenre).isNotNull();
        assertThat(updatedGenre.getId()).isEqualTo(existsGenre.getId());
        assertThat(updatedGenre.getName()).isEqualTo("test genre");
    }

    @DisplayName("должен удалить жанр по id")
    @Test
    void deleteBookById() {
        GenreDto actualGenres = genreService.findAll().get(0);

        genreService.deleteById(actualGenres.getId());
        Optional<GenreDto> actualGenre = genreService.findById(actualGenres.getId());

        assertThat(actualGenre).isEmpty();
    }
}
