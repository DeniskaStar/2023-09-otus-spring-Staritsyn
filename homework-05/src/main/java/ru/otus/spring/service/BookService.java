package ru.otus.spring.service;

import ru.otus.spring.dto.book.BookModel;
import ru.otus.spring.dto.book.BookRequestModel;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookModel> findAll();

    Optional<BookModel> findById(long id);

    BookModel save(BookRequestModel book);

    void deleteById(long id);
}
