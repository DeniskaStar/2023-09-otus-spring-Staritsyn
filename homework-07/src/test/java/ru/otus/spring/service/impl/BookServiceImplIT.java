package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;
import ru.otus.spring.service.BookService;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест сервиса для работы с авторами")
@Sql({"classpath:sql/data.sql"})
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class BookServiceImplIT {

    @Autowired
    private BookService bookService;

    @DisplayName("должен вернуть список книг")
    @Test
    void findAll_shouldReturnAllBooks() {
        var actualBooks = bookService.findAll();

        assertThat(actualBooks).hasSize(3)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getTitle()))
                .allMatch(it -> it.getAuthor() != null)
                .allMatch(it -> !CollectionUtils.isEmpty(it.getGenres()));
    }

    @DisplayName("должен вернуть книгу по id, если существует")
    @Test
    void findById_shouldReturnBook_whenBookExists() {
        Optional<BookDto> actualBook = bookService.findById(1L);

        assertThat(actualBook).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("title", "BookTitle_1");
    }

    @DisplayName("должен вернуть пустой элемент, если книги по id не существует")
    @Test
    void findById_shouldReturnEmptyBook_whenBookNotExists() {
        Optional<BookDto> actualBook = bookService.findById(8L);

        assertThat(actualBook).isEmpty();
    }

    @DisplayName("должен сохранить кингу")
    @Test
    void save() {
        var bookCreateRequest = new BookCreateDto("Book_title_test", 1L, Set.of(1L));

        var expectedBook = bookService.create(bookCreateRequest);
        var actualBook = bookService.findById(expectedBook.getId());

        assertThat(actualBook).isPresent().get()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен обновить книгу")
    @Test
    void update() {
        var bookUpdateRequest = new BookUpdateDto(1L, "Title_test", 2L, Set.of(1L));

        var actualBook = bookService.update(bookUpdateRequest);

        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isEqualTo(1L);
        assertThat(actualBook.getAuthor().getId()).isEqualTo(2L);
        assertThat(actualBook.getTitle()).isEqualTo("Title_test");
    }

    @DisplayName("должен удалить книгу по id")
    @Test
    void deleteBookById() {
        bookService.deleteById(1L);

        Optional<BookDto> actualBook = bookService.findById(1L);

        assertThat(actualBook).isEmpty();
    }
}
