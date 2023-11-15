package ru.otus.spring.data.repository;

import ru.otus.spring.data.domain.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreRepository {

    List<Genre> findAll();

    List<Genre> findByIds(Collection<Long> genreIds);

    Genre save(Genre genre);

    void deleteById(long id);
}
