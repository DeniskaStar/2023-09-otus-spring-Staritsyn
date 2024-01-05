package ru.otus.spring.data.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.data.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph("Book.authors")
    List<Book> findAll();

    @EntityGraph("Book.authors-genres")
    Optional<Book> findById(long id);
}
