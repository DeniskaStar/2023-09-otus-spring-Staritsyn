package ru.otus.spring.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.dto.author.AuthorDto;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;
import ru.otus.spring.dto.genre.GenreDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DisplayName("Тест BookController для работы с книгами")
@WebFluxTest(BookController.class)
public class BookControllerTest {

    private final AuthorDto author = new AuthorDto(1L, "Author First");

    private final GenreDto genre = new GenreDto(1L, "Genre First");

    private final List<BookDto> books = List.of(
            new BookDto(1L, "Book First", author, Set.of(genre)),
            new BookDto(2L, "Book Second", author, Set.of(genre))
    );

    @MockBean
    private BookService bookService;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("должен вернуть корректный список книги")
    @Test
    public void findAll() {
        when(bookService.findAll()).thenReturn(Flux.fromIterable(books));

        webTestClient.get().uri("/api/v1/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(2);
    }

    @DisplayName("должен вернуть существующую книгу по ID")
    @Test
    void findById_shouldReturnBookById_whenBookExists() {
        when(bookService.findById(anyLong())).thenReturn(Mono.just(books.get(0)));

        webTestClient.get().uri("/api/v1/books/{id}", books.get(0).getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult()
                .getResponseBody();
    }

    @DisplayName("должен вернуть 404 при несуществующей книги по ID")
    @Test
    void findById_shouldReturnNotFound_whenBookNotExists() {
        when(bookService.findById(anyLong())).thenThrow(new NotFoundException("Book not found"));

        webTestClient.get()
                .uri("/api/v1/books/{id}", books.get(0).getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    @DisplayName("должен сохранить книгу при корректном запросе")
    @Test
    public void create_shouldReturnCreated_whenRequestIsValid() {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setTitle("Test Book");
        bookCreateDto.setAuthorId(author.getId());
        bookCreateDto.setGenreIds(Set.of(genre.getId()));

        when(bookService.create(bookCreateDto)).thenReturn(Mono.just(new Book(1L)));

        webTestClient.post()
                .uri("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body((BodyInserters.fromValue(bookCreateDto)))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueMatches("Location", "/api/v1/books/[0-9]+");
    }

    @DisplayName("должен вернуть 400 при не корректном запросе на сохранение")
    @Test
    public void create_shouldReturnBadRequest_whenRequestIsNotValid() {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setTitle("Test Book");

        when(bookService.create(bookCreateDto)).thenThrow(new WebExchangeBindException(null, null));

        webTestClient.post()
                .uri("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreateDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @DisplayName("должен обновить книгу при корректном запросе")
    @Test
    public void update_shouldReturnNoContent_whenRequestIsValid() {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setTitle("Updated Book Title");
        bookUpdateDto.setAuthorId(author.getId());
        bookUpdateDto.setGenreIds(Set.of(genre.getId()));

        when(bookService.update(books.get(0).getId(), bookUpdateDto)).thenReturn(Mono.just(new Book(1L)));

        webTestClient.put()
                .uri("/api/v1/books/{id}", books.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body((BodyInserters.fromValue(bookUpdateDto)))
                .exchange()
                .expectStatus().isNoContent();
    }

    @DisplayName("должен удалить книгу по ID")
    @Test
    public void delete_shouldReturnNoContent() {
        given(bookService.deleteById(any())).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/books/{id}", books.get(0).getId())
                .exchange()
                .expectStatus().isNoContent();
    }
}
