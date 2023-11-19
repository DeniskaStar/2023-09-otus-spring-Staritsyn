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
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Comment;
import ru.otus.spring.data.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест JPA-репозитория для работы с жанра")
@Sql({"classpath:sql/data.sql"})
@Import(value = {JpaCommentRepository.class})
@ActiveProfiles("test")
@DataJpaTest
class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("должен вернуть список комментариев по id книги")
    @Test
    void findAll_shouldReturnAllCommentsByBookId() {
        var actualGenres = commentRepository.findAllByBookId(1L);

        assertThat(actualGenres).hasSize(3)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getText()))
                .allMatch(it -> it.getBook().getId().equals(1L));
    }

    @DisplayName("должен вернуть комментарий по id, если существует")
    @Test
    void findById_shouldReturnAuthor_whenAuthorExists() {
        Optional<Comment> actualAuthor = commentRepository.findById(1);

        assertThat(actualAuthor).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("text", "Comment_1_book_1");
    }

    @DisplayName("должен вернуть пустой элемент, если комментария по id не существует")
    @Test
    void findById_shouldReturnEmptyOptional_whenAuthorNotExists() {
        Optional<Comment> actualAuthor = commentRepository.findById(7);

        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("должен сохранить комментарий")
    @Test
    void create() {
        var newComment = new Comment(null, "Comment_test", new Book(1L, "Comment_title",
                new Author(1L, "Author_1"),
                List.of(new Genre(1L, "Genre_1"), new Genre(2L, "Genre_2"))));

        var expectedComment = commentRepository.save(newComment);
        var actualComment = commentRepository.findById(expectedComment.getId());

        assertThat(actualComment).isPresent()
                .get().isEqualTo(expectedComment);
    }

    @DisplayName("должен обновить комментарий")
    @Test
    void update() {
        Optional<Comment> maybeComment = commentRepository.findById(1L);
        assertThat(maybeComment).isPresent();
        var existsAuthor = maybeComment.get();
        existsAuthor.setText("Comment_test");

        var updatedAuthor = commentRepository.save(existsAuthor);

        assertThat(updatedAuthor).isNotNull();
        assertThat(updatedAuthor.getId()).isEqualTo(1L);
        assertThat(updatedAuthor.getText()).isEqualTo("Comment_test");
    }

    @DisplayName("должен удалить комментарий по id")
    @Test
    void deleteBookById() {
        Optional<Comment> maybeComment = commentRepository.findById(1);
        assertThat(maybeComment).isPresent();
        testEntityManager.clear();

        commentRepository.deleteById(1);
        Optional<Comment> deletedBook = commentRepository.findById(1);

        assertThat(deletedBook).isEmpty();
    }
}
