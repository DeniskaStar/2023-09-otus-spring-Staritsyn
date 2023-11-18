package ru.otus.spring.data.repository;

import ru.otus.spring.data.domain.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAll();

    Optional<Genre> findById(long id);

    List<Genre> findByIds(Collection<Long> genreIds);

    Genre save(Genre genre);

    void deleteById(long id);
}
