package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.BookMapper;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.data.repository.AuthorRepository;
import ru.otus.spring.data.repository.BookRepository;
import ru.otus.spring.data.repository.GenreRepository;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;
import ru.otus.spring.exception.NotFoundException;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto);
    }

    @Override
    @Transactional
    public BookDto create(BookCreateDto createBook) {
        var savedBook = bookRepository.save(prepareBookToCreate(createBook));
        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto update(BookUpdateDto updateBook) {
        validateExistsBook(updateBook);
        var updatedBook = bookRepository.save(prepareBookToUpdate(updateBook));
        return bookMapper.toDto(updatedBook);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book prepareBookToCreate(BookCreateDto book) {
        var author = findAuthor(book.getAuthorId());
        var genres = findGenres(book.getGenreIds());
        return bookMapper.toEntity(book, author, genres);
    }

    private Author findAuthor(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author [id: %d] not found".formatted(authorId)));
    }

    private List<Genre> findGenres(Set<Long> genreIds) {
        if (genreIds.isEmpty()) {
            throw new NotFoundException("Genres [ids: %s] not founds".formatted(genreIds));
        }
        var genres = genreRepository.findByIdIn(genreIds);
        validateGenres(genres, genreIds);
        return genres;
    }

    private void validateGenres(List<Genre> genres, Set<Long> genreIds) {
        if (genreIds.size() != genres.size()) {
            throw new NotFoundException("Genres [ids: %s] not all found".formatted(genreIds));
        }

        Set<Long> existGenreIds = genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());

        boolean existsAllGenres = existGenreIds.containsAll(genreIds);

        if (!existsAllGenres) {
            throw new NotFoundException("Genres [ids: %s] not all found".formatted(genreIds));
        }
    }

    private void validateExistsBook(BookUpdateDto updateBook) {
        var existsBook = findById(updateBook.getId());
        if (existsBook.isEmpty()) {
            throw new NotFoundException("Book [id: %d] not found".formatted(updateBook.getId()));
        }
    }

    private Book prepareBookToUpdate(BookUpdateDto updateBook) {
        var book = prepareBookToCreate(updateBook);
        book.setId(updateBook.getId());
        return book;
    }
}
