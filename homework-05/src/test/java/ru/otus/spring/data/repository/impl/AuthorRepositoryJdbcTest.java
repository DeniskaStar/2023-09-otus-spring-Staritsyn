package ru.otus.spring.data.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;
import ru.otus.spring.data.domain.Author;

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
        var actualBooks = authorRepository.findAll();

        assertThat(actualBooks).hasSize(3)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getFullName()));
    }

    @DisplayName("должен вернуть автора по id, если существует")
    @Test
    void findById_shouldReturnAuthor_whenAuthorExists() {
        Optional<Author> actualAuthor = authorRepository.findById(1);

        assertThat(actualAuthor).isPresent();
        assertThat(actualAuthor.get().getFullName()).isEqualTo("Author_1");
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
        var newAuthor = new Author(null, "Author_test");

        var savedAuthor = authorRepository.save(newAuthor);

        assertThat(savedAuthor).isNotNull();
        var existsAuthor = authorRepository.findById(savedAuthor.getId());
        assertThat(existsAuthor).isPresent();
        assertThat(existsAuthor.get().getId()).isEqualTo(4L);
    }
}
