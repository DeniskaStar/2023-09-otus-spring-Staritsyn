package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.comment.CommentCreateDto;
import ru.otus.spring.dto.comment.CommentDto;
import ru.otus.spring.dto.comment.CommentUpdateDto;
import ru.otus.spring.service.CommentService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест сервиса для работы с жанрами")
@Sql({"classpath:sql/data.sql"})
@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class CommentServiceImplIT {

    @Autowired
    private CommentService commentService;

    @DisplayName("должен вернуть список комментариев по id книги")
    @Test
    void findAll_shouldReturnAllCommentsByBookId() {
        var actualGenres = commentService.findAllByBookId(1L);

        assertThat(actualGenres).hasSize(3)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getText()));
    }

    @DisplayName("должен вернуть комментарий по id, если существует")
    @Test
    void findById_shouldReturnAuthor_whenAuthorExists() {
        Optional<CommentDto> actualAuthor = commentService.findById(1L);

        assertThat(actualAuthor).isPresent().get()
                .hasFieldOrPropertyWithValue("text", "Comment_1_book_1");
    }

    @DisplayName("должен вернуть пустой элемент, если комментария по id не существует")
    @Test
    void findById_shouldReturnEmptyOptional_whenAuthorNotExists() {
        Optional<CommentDto> actualAuthor = commentService.findById(7L);

        assertThat(actualAuthor).isEmpty();
    }

    @DisplayName("должен сохранить комментарий")
    @Test
    void create() {
        var commentCreateRequest = new CommentCreateDto("Comment_test", 1L);

        var expectedComment = commentService.create(commentCreateRequest);
        var actualComment = commentService.findById(expectedComment.getId());

        assertThat(actualComment).isPresent().get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен обновить комментарий")
    @Test
    void update() {
        var commentUpdateRequest = new CommentUpdateDto(1L, "Updated_comment", 1L);

        var actualComment = commentService.update(commentUpdateRequest);

        assertThat(actualComment).isNotNull();
        assertThat(actualComment.getId()).isEqualTo(1L);
        assertThat(actualComment.getText()).isEqualTo("Updated_comment");
    }

    @DisplayName("должен удалить комментарий по id")
    @Test
    void deleteBookById() {
        commentService.deleteById(1L);

        var actualComment = commentService.findById(1L);

        assertThat(actualComment).isEmpty();
    }
}
