package ru.otus.spring.service;

import ru.otus.spring.dto.book.BookCreateEditDto;
import ru.otus.spring.dto.book.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDto> findAll();

    Optional<BookDto> findById(String id);

    List<BookDto> findAllByAuthorId(String authorId);

    BookDto create(BookCreateEditDto book);

    BookDto update(String id, BookCreateEditDto book);

    void deleteById(String id);
}
