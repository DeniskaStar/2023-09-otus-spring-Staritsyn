package ru.otus.spring.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.dto.book.BookCreateEditDto;
import ru.otus.spring.dto.book.BookDto;

import java.util.List;
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
        bookDto.setGenres((book.getGenres().stream().map(genreMapper::toDto).toList()));
        return bookDto;
    }

    public Book toEntity(BookCreateEditDto bookRequest, Author author, List<Genre> genres) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(author);
        book.setGenres(genres);
        return book;
    }

    public String bookToString(BookDto book) {
        var genresString = book.getGenres().stream()
                .map(genreMapper::genreToString)
                .map("{%s}"::formatted)
                .collect(Collectors.joining(", "));
        return "Id: %s, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorMapper.authorToString(book.getAuthor()),
                genresString);
    }
}
