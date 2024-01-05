package ru.otus.spring.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.data.domain.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findByIdIn(Collection<Long> genreIds);
}
