package ru.otus.spring.data.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.spring.data.domain.Author;

public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {
}
