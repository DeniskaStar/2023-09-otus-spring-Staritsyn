package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.author.AuthorCreateDto;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.author.AuthorUpdateDto;
import ru.otus.spring.service.AuthorService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест сервиса для работы с авторами")
@Sql({"classpath:sql/data.sql"})
@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class AuthorServiceImplIT {

    @Autowired
    private AuthorService authorService;

    @DisplayName("должен вернуть список авторов")
    @Test
    void findAllByBookId_shouldReturnAllAuthors() {
        var actualBooks = authorService.findAll();

        assertThat(actualBooks).hasSize(3)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getFullName()));
    }

    @DisplayName("должен вернуть автора по id, если существует")
    @Test
    void findById_shouldReturnAuthor_whenAuthorExists() {
        Optional<AuthorDto> actualAuthor = authorService.findById(1L);

        assertThat(actualAuthor).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("fullName", "Author_1");
    }

    @DisplayName("должен вернуть пустой элемент, если автора по id не существует")
    @Test
    void findById_shouldReturnEmptyOptional_whenAuthorNotExists() {
        Optional<AuthorDto> actualAuthor = authorService.findById(5L);

        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("должен сохранить автора")
    @Test
    void save() {
        var createRequest = new AuthorCreateDto("Author_test");

        var expectedAuthor = authorService.create(createRequest);
        var actualAuthor = authorService.findById(expectedAuthor.getId());

        assertThat(actualAuthor).isPresent().get()
                .isEqualTo(expectedAuthor);
    }

    @DisplayName("должен обновить автора")
    @Test
    void update() {
        var updateRequest = new AuthorUpdateDto(1L, "Author_test");

        var actualAuthor = authorService.update(updateRequest);

        assertThat(actualAuthor).isNotNull();
        assertThat(actualAuthor.getId()).isEqualTo(1L);
        assertThat(actualAuthor.getFullName()).isEqualTo("Author_test");
    }

    @DisplayName("должен удалить автора по id")
    @Test
    void deleteBookById() {
        authorService.deleteById(1L);

        var actualAuthor = authorService.findById(1L);

        assertThat(actualAuthor).isEmpty();
    }
}
