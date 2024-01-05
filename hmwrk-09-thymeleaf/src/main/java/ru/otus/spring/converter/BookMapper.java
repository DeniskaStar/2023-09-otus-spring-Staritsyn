package ru.otus.spring.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;
import ru.otus.spring.dto.book.BookUpdateDto;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(authorMapper.toDto(book.getAuthor()));
        bookDto.setGenres((book.getGenres().stream().map(genreMapper::toDto).collect(Collectors.toSet())));
        return bookDto;
    }

    public Book toEntity(BookCreateDto bookCreateDto, Author author, Set<Genre> genres) {
        Book book = new Book();
        book.setTitle(bookCreateDto.getTitle());
        book.setAuthor(author);
        book.setGenres(genres);
        return book;
    }

    public Book toEntity(Book existingBook, BookUpdateDto bookUpdateDto, Author author, Set<Genre> genres) {
        existingBook.setTitle(bookUpdateDto.getTitle());
        existingBook.setAuthor(author);
        existingBook.setGenres(genres);
        return existingBook;
    }
}
