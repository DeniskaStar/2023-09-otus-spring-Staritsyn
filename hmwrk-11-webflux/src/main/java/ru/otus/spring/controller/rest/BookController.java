package ru.otus.spring.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;
import ru.otus.spring.service.BookService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "api/v1/books", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BookDto> findAll() {
        return bookService.findAll();
    }

    @GetMapping("api/v1/books/{id}")
    public Mono<ResponseEntity<BookDto>> findById(@PathVariable("id") long bookId) {
        return bookService.findById(bookId)
                .map(ResponseEntity::ok);
    }

    @PostMapping("api/v1/books")
    public Mono<ResponseEntity<Void>> create(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return bookService.create(bookCreateDto)
                .map(savedBook -> ResponseEntity.created(URI.create("/api/v1/books/" + savedBook.getId())).build());
    }

    @PutMapping("api/v1/books/{id}")
    public Mono<ResponseEntity<Void>> update(@PathVariable("id") long bookId,
                                             @Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.update(bookId, bookUpdateDto)
                .map(updatedBook -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("api/v1/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") long id) {
        return bookService.deleteById(id);
    }
}
