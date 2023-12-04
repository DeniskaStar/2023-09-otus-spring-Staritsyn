package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.author.AuthorCreateEditDto;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест сервиса для работы с авторами")
@ActiveProfiles("test")
@SpringBootTest
public class AuthorServiceImplIT {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен вернуть список авторов")
    @Test
    void findAll_shouldReturnAllAuthors() {
        List<AuthorDto> actualBooks = authorService.findAll();

        assertThat(actualBooks).hasSize(2)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getFullName()));
    }

    @DisplayName("должен вернуть автора по id, если существует")
    @Test
    void findById_shouldReturnAuthor_whenAuthorExists() {
        AuthorCreateEditDto createAuthorRequest = new AuthorCreateEditDto("Test Author");
        AuthorDto createdAuthor = authorService.create(createAuthorRequest);

        Optional<AuthorDto> actualAuthor = authorService.findById(createdAuthor.getId());

        assertThat(actualAuthor).isPresent().get()
                .hasFieldOrPropertyWithValue("fullName", "Test Author");
    }

    @DisplayName("должен вернуть пустой элемент, если автора по id не существует")
    @Test
    void findById_shouldReturnEmptyOptional_whenAuthorNotExists() {
        Optional<AuthorDto> actualAuthor = authorService.findById("dummy");

        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("должен сохранить автора")
    @Test
    void save() {
        AuthorCreateEditDto createAuthorRequest = new AuthorCreateEditDto("Test Author");
        AuthorDto expectedAuthor = authorService.create(createAuthorRequest);

        var actualAuthor = authorService.findById(expectedAuthor.getId());

        assertThat(actualAuthor).isPresent().get()
                .isEqualTo(expectedAuthor);
    }

    @DisplayName("должен обновить автора")
    @Test
    void update() {
        AuthorDto existAuthor = authorService.findAll().get(0);
        AuthorCreateEditDto updateAuthorRequest = new AuthorCreateEditDto("Dummy Author");

        AuthorDto actualAuthor = authorService.update(existAuthor.getId(), updateAuthorRequest);

        assertThat(actualAuthor).isNotNull();
        assertThat(actualAuthor.getId()).isEqualTo(existAuthor.getId());
        assertThat(actualAuthor.getFullName()).isEqualTo("Dummy Author");
    }

    @DisplayName("должен удалить автора по id")
    @Test
    void deleteById() {
        AuthorDto existAuthor = authorService.findAll().get(0);

        authorService.deleteById(existAuthor.getId());
        Optional<AuthorDto> actualAuthor = authorService.findById(existAuthor.getId());

        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("должен удалить книги при удалении автора")
    @Test
    void deleteId_shouldDeleteBooksByAuthor() {
        AuthorDto existAuthor = authorService.findAll().get(0);
        List<BookDto> booksByAuthor = bookService.findAllByAuthorId(existAuthor.getId());
        assertThat(booksByAuthor).hasSize(1);

        authorService.deleteById(existAuthor.getId());
        booksByAuthor = bookService.findAllByAuthorId(existAuthor.getId());

        assertThat(booksByAuthor).isEmpty();
    }
}

