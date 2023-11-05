package ru.otus.spring.data.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.util.providers.TestAuthorProvider;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест JDBC-репозитория для работы с авторами")
@Sql({"classpath:sql/data.sql"})
@Import(value = {AuthorRepositoryJdbc.class})
@JdbcTest
class AuthorRepositoryJdbcTest {

    @Autowired
    private AuthorRepositoryJdbc authorRepository;

    @DisplayName("должен вернуть список авторов")
    @Test
    void findAll_shouldReturnAllAuthors() {
        var expectedAuthors = TestAuthorProvider.getAllAuthors();

        var actualBooks = authorRepository.findAll();

        assertThat(actualBooks).hasSize(3);
        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @DisplayName("должен вернуть автора по id, если существует")
    @Test
    void findById_shouldReturnAuthor_whenAuthorExists() {
        Author expectedAuthor = TestAuthorProvider.getOneAuthor();

        Optional<Author> actualAuthor = authorRepository.findById(1);

        assertThat(actualAuthor).isPresent();
        assertThat(actualAuthor.get()).isEqualTo(expectedAuthor);
    }

    @DisplayName("должен вернуть пустой элемент, если автора по id не существует")
    @Test
    void findById_shouldReturnEmptyOptional_whenAuthorNotExists() {
        Optional<Author> actualAuthor = authorRepository.findById(5);

        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("должен сохранить автора")
    @Test
    void save() {
        var newAuthor = TestAuthorProvider.createOneAuthorWithoutId();

        var savedAuthor = authorRepository.save(newAuthor);

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(newAuthor);
    }
}
