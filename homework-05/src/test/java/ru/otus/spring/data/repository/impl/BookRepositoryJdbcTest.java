package ru.otus.spring.data.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест JDBC-репозитория для работы с книгами")
@Sql({"classpath:sql/data.sql"})
@Import({BookRepositoryJdbc.class, GenreRepositoryJdbc.class})
@JdbcTest
class BookRepositoryJdbcTest {

    @Autowired
    private BookRepositoryJdbc bookRepository;

    @DisplayName("должен вернуть список книг")
    @Test
    void findAll_shouldReturnAllBooks() {
        var actualBooks = bookRepository.findAll();

        assertThat(actualBooks).hasSize(3)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getTitle()))
                .allMatch(it -> it.getAuthor() != null)
                .allMatch(it -> !CollectionUtils.isEmpty(it.getGenres()));
    }

    @DisplayName("должен вернуть книгу по id, если существует")
    @Test
    void findById_shouldReturnBook_whenBookExists() {
        var actualBook = bookRepository.findById(1L);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().getTitle()).isEqualTo("BookTitle_1");
    }

    @DisplayName("должен вернуть пустой элемент, если книги по id не существует")
    @Test
    void findById_shouldReturnEmptyBook_whenBookNotExists() {
        var actualBook = bookRepository.findById(8L);

        assertThat(actualBook).isEmpty();
    }

    @DisplayName("должен сохранить кингу")
    @Test
    void save() {
        var newBook = new Book(null,
                "Title_test",
                new Author(2L, "Author_2"),
                List.of(new Genre(2L, "Genre_2")));

        var savedBook = bookRepository.save(newBook);

        assertThat(savedBook).isNotNull();
        var existsBook = bookRepository.findById(savedBook.getId());
        assertThat(existsBook).isPresent();
        assertThat(existsBook.get().getId()).isEqualTo(4L);
    }

    @DisplayName("должен обновить книгу")
    @Test
    void update() {
        Optional<Book> maybeBook = bookRepository.findById(1L);
        assertThat(maybeBook).isPresent();
        var existsBook = maybeBook.get();
        existsBook.setTitle("Title_test");

        Book updatedBook = bookRepository.save(existsBook);

        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getId()).isEqualTo(1L);
        assertThat(updatedBook.getTitle()).isEqualTo("Title_test");
    }

    @DisplayName("должен удалить книгу по id")
    @Test
    void deleteBookById() {
        var maybeBook = bookRepository.findById(1);
        assertThat(maybeBook).isPresent();

        bookRepository.deleteById(1);
        Optional<Book> deletedBook = bookRepository.findById(1);

        assertThat(deletedBook).isEmpty();
    }
}
