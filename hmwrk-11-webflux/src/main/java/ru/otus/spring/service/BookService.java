package ru.otus.spring.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;

public interface BookService {

    Flux<BookDto> findAll();

    Mono<BookDto> findById(Long bookId);

    Mono<Book> create(BookCreateDto bookCreateDto);

    Mono<Book> update(Long bookId, BookUpdateDto bookUpdateDto);

    Mono<Void> deleteById(Long bookId);
}
