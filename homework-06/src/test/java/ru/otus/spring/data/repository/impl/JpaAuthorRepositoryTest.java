package ru.otus.spring.data.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;
import ru.otus.spring.data.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест JPA-репозитория для работы с авторами")
@Sql({"classpath:sql/data.sql"})
@Import(value = {JpaAuthorRepository.class})
@ActiveProfiles("test")
@DataJpaTest
class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository authorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("должен вернуть список авторов")
    @Test
    void findAllByBookId_shouldReturnAllAuthors() {
        var actualBooks = authorRepository.findAll();

        assertThat(actualBooks).hasSize(3)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getFullName()));
    }

    @DisplayName("должен вернуть автора по id, если существует")
    @Test
    void findById_shouldReturnAuthor_whenAuthorExists() {
        Optional<Author> actualAuthor = authorRepository.findById(1);

        assertThat(actualAuthor).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("fullName", "Author_1");
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

        var expectedAuthor = authorRepository.save(newAuthor);
        var actualAuthor = authorRepository.findById(expectedAuthor.getId());

        assertThat(actualAuthor).isPresent().get()
                .isEqualTo(expectedAuthor);
    }

    @DisplayName("должен обновить автора")
    @Test
    void update() {
        Optional<Author> maybeAuthor = authorRepository.findById(1L);
        assertThat(maybeAuthor).isPresent();
        var existsAuthor = maybeAuthor.get();
        existsAuthor.setFullName("Author_test");

        var updatedAuthor = authorRepository.save(existsAuthor);

        assertThat(updatedAuthor).isNotNull();
        assertThat(updatedAuthor.getId()).isEqualTo(1L);
        assertThat(updatedAuthor.getFullName()).isEqualTo("Author_test");
    }

    @DisplayName("должен удалить автора по id")
    @Test
    void deleteBookById() {
        Optional<Author> maybeAuthor = authorRepository.findById(1);
        assertThat(maybeAuthor).isPresent();
        testEntityManager.clear();

        authorRepository.deleteById(1);
        Optional<Author> deletedAuthor = authorRepository.findById(1);

        assertThat(deletedAuthor).isEmpty();
    }
}
