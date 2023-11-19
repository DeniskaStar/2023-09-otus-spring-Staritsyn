package ru.otus.spring.data.repository;

import ru.otus.spring.data.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    List<Author> findAll();

    Optional<Author> findById(long id);

    Author save(Author author);

    void deleteById(long id);
}
