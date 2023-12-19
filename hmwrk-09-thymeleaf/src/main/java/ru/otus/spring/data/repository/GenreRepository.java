package ru.otus.spring.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.data.domain.Genre;

import java.util.Collection;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Set<Genre> findByIdIn(Collection<Long> genreIds);
}
