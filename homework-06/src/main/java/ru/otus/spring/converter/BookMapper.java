package ru.otus.spring.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.dto.book.BookCreateDto;
import ru.otus.spring.dto.book.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        if (bookDto.getAuthor() != null) {
            book.setAuthor(authorMapper.toEntity(bookDto.getAuthor()));
        }
        if (!CollectionUtils.isEmpty(bookDto.getGenres())) {
            book.setGenres((bookDto.getGenres().stream().map(genreMapper::toEntity).toList()));
        }
        return book;
    }


    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        if (book.getAuthor() != null) {
            bookDto.setAuthor(authorMapper.toDto(book.getAuthor()));
        }
        if (!CollectionUtils.isEmpty(book.getGenres())) {
            bookDto.setGenres((book.getGenres().stream().map(genreMapper::toDto).toList()));
        }
        return bookDto;
    }

    public Book toEntity(BookCreateDto bookRequest, Author author, List<Genre> genres) {
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
        return "Id: %d, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorMapper.authorToString(book.getAuthor()),
                genresString);
    }
}
