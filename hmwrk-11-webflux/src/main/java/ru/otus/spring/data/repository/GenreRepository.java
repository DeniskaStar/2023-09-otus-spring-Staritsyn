package ru.otus.spring.data.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.data.domain.Genre;

import java.util.Collection;

public interface GenreRepository extends ReactiveCrudRepository<Genre, Long> {

    @Query("select g.* from genres g join books_genres bg on bg.genre_id = g.id where bg.book_id = :bookId")
    Flux<Genre> findGenresByBookId(Long bookId);

    Mono<Boolean> existsAllByIdIn(Collection<Long> genreIds);
}
