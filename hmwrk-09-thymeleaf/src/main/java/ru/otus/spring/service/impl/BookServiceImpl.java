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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public BookDto findById(long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Book [id: %d] not found".formatted(id)));
    }

    @Override
    @Transactional
    public BookDto create(BookCreateDto bookCreateDto) {
        var savedBook = bookRepository.save(prepareBookToCreate(bookCreateDto));
        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto update(BookUpdateDto bookUpdateDto) {
        Book existingBook = bookRepository.findById(bookUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("Book [id: %d] not found"
                        .formatted(bookUpdateDto.getId())));
        var updatedBook = bookRepository.save(prepareBookToUpdate(bookUpdateDto, existingBook));
        return bookMapper.toDto(updatedBook);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book [id %d] not found".formatted(id));
        }
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

    private Set<Genre> findGenres(Set<Long> genreIds) {
        if (genreIds.isEmpty()) {
            throw new NotFoundException("Genres [ids: %s] not founds".formatted(genreIds));
        }
        var genres = genreRepository.findByIdIn(genreIds);
        if (genreIds.size() != genres.size()) {
            throw new NotFoundException("Genres [ids: %s] not all found".formatted(genreIds));
        }
        return new HashSet<>(genres);
    }

    private Book prepareBookToUpdate(BookUpdateDto updateBook, Book existingBook) {
        var author = findAuthor(updateBook.getAuthorId());
        var genres = findGenres(updateBook.getGenreIds());
        return bookMapper.toEntity(existingBook, updateBook, author, genres);
    }
}
