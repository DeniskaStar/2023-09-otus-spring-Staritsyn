package ru.otus.spring.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.data.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
