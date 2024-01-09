package ru.otus.spring.data.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.data.domain.BooksGenres;

public interface BooksGenresRepository extends ReactiveCrudRepository<BooksGenres, Long> {

    Flux<BooksGenres> findAllByBookId(Long bookId);
}
