package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.converter.BookMapper;
import ru.otus.spring.converter.GenreMapper;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.BooksGenres;
import ru.otus.spring.data.repository.AuthorRepository;
import ru.otus.spring.data.repository.BookRepository;
import ru.otus.spring.data.repository.BooksGenresRepository;
import ru.otus.spring.data.repository.GenreRepository;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.BookService;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final BooksGenresRepository booksGenresRepository;

    private final BookMapper bookMapper;

    private final GenreMapper genreMapper;

    @Override
    public Flux<BookDto> findAll() {
        return bookRepository.findAll()
                .flatMap(this::loadRelations)
                .map(bookMapper::toDto);
    }

    @Override
    public Mono<BookDto> findById(Long bookId) {
        return bookRepository.findById(bookId)
                .flatMap(this::loadRelations)
                .map(bookMapper::toDto)
                .switchIfEmpty(Mono.error(new NotFoundException("Book [bookId: %d] not found".formatted(bookId))));
    }

    @Override
    public Mono<Book> create(BookCreateDto bookCreateDto) {
        return authorRepository.findById(bookCreateDto.getAuthorId())
                .flatMap(author -> {
                    // Проверка существования жанров
                    return genreRepository.findAllById(bookCreateDto.getGenreIds())
                            .collectList()
                            .flatMap(genres -> {
                                if (genres.size() != bookCreateDto.getGenreIds().size()) {
                                    return Mono.error(new NotFoundException("Genres [id: %s] not found"
                                            .formatted(bookCreateDto.getGenreIds())));
                                }
                                // Создание книги
                                return bookRepository.save(bookMapper.toEntity(bookCreateDto))
                                        .flatMap(savedBook ->
                                                // Сохраняем жанры
                                                booksGenresRepository.saveAll(genreMapper.toBookGenre(
                                                                savedBook.getId(),
                                                                bookCreateDto.getGenreIds()))
                                                        .collectList()
                                                        .then(Mono.just(savedBook)));
                            });
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Author [id: %d] not found"
                        .formatted(bookCreateDto.getAuthorId()))));
    }

    @Override
    public Mono<Book> update(Long bookId, BookUpdateDto bookUpdateDto) {
        return authorRepository.findById(bookUpdateDto.getAuthorId())
                .flatMap(author -> {
                    // Проверка существования жанров
                    return genreRepository.findAllById(bookUpdateDto.getGenreIds())
                            .collectList()
                            .flatMap(genres -> {
                                if (genres.size() != bookUpdateDto.getGenreIds().size()) {
                                    return Mono.error(new NotFoundException("Genres [id: %s] not found"
                                            .formatted(bookUpdateDto.getGenreIds())));
                                }
                                return bookRepository.findById(bookId)
                                        .switchIfEmpty(Mono.error(new NotFoundException("Book [id: %d] not found"
                                                .formatted(bookId))))
                                        .flatMap(book -> {
                                            bookMapper.copy(book, bookUpdateDto);
                                            return updateBooksGenres(bookId, bookUpdateDto.getGenreIds())
                                                    .then(bookRepository.save(book));
                                        });
                            });
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Author [id: %d] not found"
                        .formatted(bookUpdateDto.getAuthorId()))));
    }

    private Mono<Void> updateBooksGenres(Long bookId, Set<Long> genreIds) {
        return booksGenresRepository.findAllByBookId(bookId)
                .collectList()
                .flatMap(currentBookGenres -> {
                    var existingGenres = genreMapper.extractGenreIdsFromBooksGenres(currentBookGenres);

                    var removedBooksGenres = currentBookGenres.stream()
                            .filter(booksGenres -> !genreIds.contains(booksGenres.getGenreId()))
                            .toList();

                    var addedBooksGenres = genreIds.stream()
                            .filter(genreId -> !existingGenres.contains(genreId))
                            .map(genreId -> new BooksGenres(bookId, genreId))
                            .toList();

                    return booksGenresRepository.deleteAll(removedBooksGenres)
                            .then(booksGenresRepository.saveAll(addedBooksGenres).then());
                });
    }

    @Override
    public Mono<Void> deleteById(Long bookId) {
        return bookRepository.deleteById(bookId);
    }

    private Mono<Book> loadRelations(Book book) {
        return Mono.just(book)
                .zipWith(genreRepository.findGenresByBookId(book.getId()).collectList())
                .map(result -> {
                    Book resultBook = result.getT1();
                    resultBook.setGenres(result.getT2());
                    return resultBook;
                })
                .zipWith(authorRepository.findById(book.getAuthorId()))
                .map(result -> {
                    Book resultBook = result.getT1();
                    resultBook.setAuthor(result.getT2());
                    return resultBook;
                });
    }
}
