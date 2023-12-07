package ru.otus.spring.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.data.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
