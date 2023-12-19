package ru.otus.spring.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;

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

    public Book toEntity(BookCreateDto bookRequest, Author author, Set<Genre> genres) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(author);
        book.setGenres(genres);
        return book;
    }
}
