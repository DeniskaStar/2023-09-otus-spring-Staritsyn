package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.converter.BookMapper;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.repository.BookRepository;
import ru.otus.spring.dto.author.AuthorModel;
import ru.otus.spring.dto.book.BookModel;
import ru.otus.spring.dto.book.BookRequestModel;
import ru.otus.spring.dto.genre.GenreModel;
import ru.otus.spring.exception.EntityByIdNotFoundException;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookModel> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toModel)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookModel> findById(long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toModel);
    }

    @Override
    @Transactional
    public BookModel save(BookRequestModel bookRequest) {
        Book savedBook = bookRepository.save(prepareBook(bookRequest));
        return bookMapper.toModel(savedBook);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Book prepareBook(BookRequestModel bookRequest) {
        var author = findAuthorOfBook(bookRequest.getAuthorId());
        var genres = findGenresOfBook(bookRequest.getGenreIds());

        return bookMapper.toDao(BookModel.builder()
                .id(bookRequest.getId())
                .title(bookRequest.getTitle())
                .author(author)
                .genres(genres)
                .build());
    }

    private AuthorModel findAuthorOfBook(long authorId) {
        return authorService.findById(authorId)
                .orElseThrow(() -> new EntityByIdNotFoundException("Author [id: %d] not found".formatted(authorId)));
    }

    private List<GenreModel> findGenresOfBook(Collection<Long> genreIds) {
        var genres = genreService.findByIds(genreIds);
        if (genres.isEmpty()) {
            throw new EntityByIdNotFoundException("Genres [ids: %s] not found".formatted(genreIds));
        }
        return genres;
    }
}
