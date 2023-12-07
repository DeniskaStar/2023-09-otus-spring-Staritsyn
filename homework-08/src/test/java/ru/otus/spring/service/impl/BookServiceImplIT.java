package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;
import ru.otus.spring.dto.comment.CommentDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест сервиса для работы с книгами")
@ActiveProfiles("test")
@SpringBootTest
class BookServiceImplIT {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CommentService commentService;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("должен вернуть список книг")
    @Test
    void findAll_shouldReturnAllBooks() {
        var actualBooks = bookService.findAll();

        assertThat(actualBooks).hasSize(2)
                .allMatch(it -> it.getId() != null)
                .allMatch(it -> StringUtils.hasText(it.getTitle()))
                .allMatch(it -> it.getAuthor() != null)
                .allMatch(it -> !CollectionUtils.isEmpty(it.getGenres()));
    }

    @DisplayName("должен вернуть книгу по id, если существует")
    @Test
    void findById_shouldReturnBook_whenBookExists() {
        BookDto book = bookService.findAll().get(0);

        Optional<BookDto> actualBook = bookService.findById(book.getId());

        assertThat(actualBook).isPresent().get()
                .hasFieldOrPropertyWithValue("title", book.getTitle());
    }

    @DisplayName("должен вернуть пустой элемент, если книги по id не существует")
    @Test
    void findById_shouldReturnEmptyBook_whenBookNotExists() {
        Optional<BookDto> actualBook = bookService.findById("dummy");

        assertThat(actualBook).isEmpty();
    }

    @DisplayName("должен сохранить кингу")
    @Test
    void save() {
        AuthorDto author = authorService.findAll().get(0);
        GenreDto genre = genreService.findAll().get(0);
        BookCreateDto bookCreateRequest = new BookCreateDto("Test Title", author.getId(), Set.of(genre.getId()));

        BookDto expectedBook = bookService.create(bookCreateRequest);
        Optional<BookDto> actualBook = bookService.findById(expectedBook.getId());

        assertThat(actualBook).isPresent().get()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен обновить книгу")
    @Test
    void update() {
        AuthorDto author = authorService.findAll().get(0);
        GenreDto genre = genreService.findAll().get(0);
        BookDto existBook = bookService.findAll().get(0);
        BookUpdateDto bookUpdateRequest = new BookUpdateDto(existBook.getId(), "Test Title", author.getId(), Set.of(genre.getId()));

        var actualBook = bookService.update(bookUpdateRequest);

        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isEqualTo(existBook.getId());
        assertThat(actualBook.getAuthor().getId()).isEqualTo(author.getId());
        assertThat(actualBook.getTitle()).isEqualTo("Test Title");
    }

    @DisplayName("должен удалить книгу по id")
    @Test
    void deleteById() {
        BookDto existBook = bookService.findAll().get(0);

        bookService.deleteById(existBook.getId());
        Optional<BookDto> actualBook = bookService.findById(existBook.getId());

        assertThat(actualBook).isEmpty();
    }

    @DisplayName("должен удалить комментарии при удалении книги")
    @Test
    void deleteById_shouldDeleteCommentsByBook() {
        BookDto existBook = bookService.findAll().get(0);

        bookService.deleteById(existBook.getId());
        List<CommentDto> commentsByBook = commentService.findAllByBookId(existBook.getId());

        assertThat(commentsByBook).isEmpty();
    }
}
