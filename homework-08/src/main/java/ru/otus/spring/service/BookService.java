package ru.otus.spring.service;

import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDto> findAll();

    Optional<BookDto> findById(String id);

    List<BookDto> findAllByAuthorId(String authorId);

    BookDto create(BookCreateDto book);

    BookDto update(BookUpdateDto book);

    void deleteById(String id);
}
