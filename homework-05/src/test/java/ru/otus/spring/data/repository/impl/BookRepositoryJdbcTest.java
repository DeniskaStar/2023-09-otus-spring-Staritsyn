package ru.otus.spring.data.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.util.providers.TestBookProvider;

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
        var expectedBook = TestBookProvider.getAllBooks();

        var actualBooks = bookRepository.findAll();

        assertThat(actualBooks).hasSize(3);
        assertThat(actualBooks).containsAnyElementsOf(expectedBook);
    }

    @DisplayName("должен вернуть книгу по id, если существует")
    @Test
    void findById_shouldReturnBook_whenBookExists() {
        var expectedBook = TestBookProvider.getOneBook();

        var actualBook = bookRepository.findById(1L);

        assertThat(actualBook).isPresent();
        assertThat(actualBook).get().isEqualTo(expectedBook);
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
        var newBook = TestBookProvider.createOneBookWithoutId();

        var savedBook = bookRepository.save(newBook);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(newBook);
    }

    @DisplayName("должен обновить книгу")
    @Test
    void update() {
        var newBook = TestBookProvider.createUpdatedOneBook();

        var savedBook = bookRepository.save(newBook);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook).isEqualTo(newBook);

        Optional<Book> updatedBook = bookRepository.findById(newBook.getId());
        assertThat(updatedBook).isPresent();
        assertThat(updatedBook).get().isEqualTo(savedBook);
    }

    @DisplayName("должен удалить книгу по id")
    @Test
    void deleteBookById() {
        var existsBook = bookRepository.findById(1);
        assertThat(existsBook).isPresent();

        bookRepository.deleteById(1);
        Optional<Book> deletedBook = bookRepository.findById(1);

        assertThat(deletedBook).isEmpty();
    }
}
