package ru.otus.spring.data.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.spring.data.domain.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
}
