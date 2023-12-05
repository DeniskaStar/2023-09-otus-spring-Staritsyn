package ru.otus.spring.service.data.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Comment;
import ru.otus.spring.data.repository.CommentRepository;
import ru.otus.spring.data.repository.book.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория для работы с комментариями через mongoDb")
@DataMongoTest
@ComponentScan({"ru.otus.spring.data.repository"})
@ActiveProfiles("test")
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен вернуть список комментириев по id книги")
    @Test
    void findAllByBookId_shouldReturnAllComments_whenBookAndCommentExists() {
        Book existBook = bookRepository.findAll().get(0);

        List<Comment> actualComments = commentRepository.findAllByBookId(existBook.getId());

        assertThat(actualComments).hasSize(2)
                .allMatch(it -> it.getBook().getId().equals(existBook.getId()));
    }

    @DisplayName("должен вернуть пустой список комментириев")
    @Test
    void findAllByBookId_shouldReturnEmptyComments_whenBookNotExists() {
        List<Comment> actualComments = commentRepository.findAllByBookId("dummy");

        assertThat(actualComments).isEmpty();
    }

    @DisplayName("должен удалить все комментирии по id книги")
    @Test
    void removeByBookId_shouldRemoveAllComments_whenBookAndCommentExists() {
        Book existBook = bookRepository.findAll().get(0);
        List<Comment> existComments = commentRepository.findAllByBookId(existBook.getId());
        assertThat(existComments).hasSize(2);

        commentRepository.removeByBookId(existBook.getId());
        List<Comment> actualComments = commentRepository.findAllByBookId(existBook.getId());

        assertThat(actualComments).isEmpty();
    }
}
