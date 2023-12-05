package ru.otus.spring.service.data.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.repository.AuthorRepository;
import ru.otus.spring.data.repository.book.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория для работы с книгами через mongoDb")
@DataMongoTest
@ComponentScan({"ru.otus.spring.data.repository"})
@ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен вернуть книги по id автора")
    @Test
    void findByAuthorId() {
        Author existAuthor = authorRepository.findAll().get(0);

        List<Book> actualBooks = bookRepository.findByAuthorId(existAuthor.getId());

        assertThat(actualBooks).isNotEmpty()
                .allMatch(it -> it.getAuthor().getId().equals(existAuthor.getId()));
    }

    @DisplayName("должен вернуть пустой список книг")
    @Test
    void findByAuthorId_shouldReturnEmptyList_whenAuthorNotExists() {
        List<Book> actualBooks = bookRepository.findByAuthorId("dummy");

        assertThat(actualBooks).isEmpty();
    }

    @DisplayName("должен удалить книги по id автора")
    @Test
    void removeByAuthorId() {
        Author existAuthor = authorRepository.findAll().get(0);
        List<Book> existBooks = bookRepository.findByAuthorId(existAuthor.getId());

        bookRepository.removeByAuthorId(existAuthor.getId());
        List<Book> actualBooks = bookRepository.findByAuthorId(existAuthor.getId());

        assertThat(actualBooks).isEmpty();
    }
}
