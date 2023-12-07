package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.comment.CommentCreateDto;
import ru.otus.spring.dto.comment.CommentDto;
import ru.otus.spring.dto.comment.CommentUpdateDto;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест сервиса для работы с жанрами")
@ActiveProfiles("test")
@SpringBootTest
public class CommentServiceImplIT {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BookService bookService;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен вернуть список комментариев по id книги")
    @Test
    void findAll_shouldReturnAllCommentsByBookId() {
        BookDto existBook = bookService.findAll().get(0);

        List<CommentDto> actualComments = commentService.findAllByBookId(existBook.getId());

        assertThat(actualComments).hasSize(2)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getText()));
    }

    @DisplayName("должен вернуть комментарий по id, если существует")
    @Test
    void findById_shouldReturnComment_whenCommentExists() {
        BookDto existBook = bookService.findAll().get(0);
        CommentDto expectedComment = commentService.findAllByBookId(existBook.getId()).get(0);

        Optional<CommentDto> actualComment = commentService.findById(expectedComment.getId());

        assertThat(actualComment).isPresent().get()
                .hasFieldOrPropertyWithValue("text", expectedComment.getText());
    }

    @DisplayName("должен вернуть пустой элемент, если комментария по id не существует")
    @Test
    void findById_shouldReturnEmptyOptional_whenCommentNotExists() {
        Optional<CommentDto> actualComment = commentService.findById("dummy");

        assertThat(actualComment).isEmpty();
    }

    @DisplayName("должен сохранить комментарий")
    @Test
    void create() {
        BookDto existBook = bookService.findAll().get(0);
        CommentCreateDto commentCreateRequest = new CommentCreateDto("Test comment", existBook.getId());

        CommentDto expectedComment = commentService.create(commentCreateRequest);
        Optional<CommentDto> actualComment = commentService.findById(expectedComment.getId());

        assertThat(actualComment).isPresent().get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен обновить комментарий")
    @Test
    void update() {
        BookDto existBook = bookService.findAll().get(0);
        CommentDto existComment = commentService.findAllByBookId(existBook.getId()).get(0);
        CommentUpdateDto expectedComment = new CommentUpdateDto(existComment.getId(), "Test comment", existBook.getId());

        CommentDto actualComment = commentService.update(expectedComment);

        assertThat(actualComment).isNotNull();
        assertThat(actualComment.getId()).isEqualTo(existComment.getId());
        assertThat(actualComment.getText()).isEqualTo("Test comment");
    }

    @DisplayName("должен удалить комментарий по id")
    @Test
    void deleteBookById() {
        BookDto existBook = bookService.findAll().get(0);
        CommentDto existComment = commentService.findAllByBookId(existBook.getId()).get(0);
        commentService.deleteById(existComment.getId());

        Optional<CommentDto> actualComment = commentService.findById(existComment.getId());

        assertThat(actualComment).isEmpty();
    }
}
