package ru.otus.spring.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.data.domain.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findByIdIn(Collection<String> genreIds);
}
