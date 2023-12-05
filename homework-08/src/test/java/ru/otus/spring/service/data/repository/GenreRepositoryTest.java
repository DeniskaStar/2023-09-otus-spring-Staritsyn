package ru.otus.spring.service.data.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.data.repository.GenreRepository;

import java.util.List;
import java.util.Set;

@DisplayName("Тест репозитория для работы с жанрами через mongoDb")
@DataMongoTest
@ComponentScan({"ru.otus.spring.data.repository"})
@ActiveProfiles("test")
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен вернуть жанры по коллекции id")
    @Test
    void findByIdIn() {
        List<Genre> allGenres = genreRepository.findAll();
        Genre firstGenre = allGenres.get(0);
        Genre secondGenre = allGenres.get(1);

        List<Genre> actualGenres = genreRepository.findByIdIn(Set.of(firstGenre.getId(), secondGenre.getId()));

        Assertions.assertThat(actualGenres).hasSize(2)
                .containsExactlyInAnyOrderElementsOf(List.of(firstGenre, secondGenre));
    }
}
