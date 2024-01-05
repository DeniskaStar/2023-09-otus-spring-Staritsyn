package ru.otus.spring.service;

import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAll();

    BookDto findById(long id);

    BookDto create(BookCreateDto book);

    BookDto update(BookUpdateDto book);

    void deleteById(long id);
}
