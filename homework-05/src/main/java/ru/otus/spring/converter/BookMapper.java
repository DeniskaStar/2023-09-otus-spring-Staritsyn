package ru.otus.spring.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.dto.book.BookModel;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public BookModel toModel(Book book) {
        BookModel bookModel = new BookModel();
        bookModel.setId(book.getId());
        bookModel.setTitle(book.getTitle());
        if (book.getAuthor() != null) {
            bookModel.setAuthor(authorMapper.toModel(book.getAuthor()));
        }
        if (!CollectionUtils.isEmpty(book.getGenres())) {
            bookModel.setGenres((book.getGenres().stream().map(genreMapper::toModel).toList()));
        }
        return bookModel;
    }

    public Book toDao(BookModel bookModel) {
        Book book = new Book();
        book.setId(bookModel.getId());
        book.setTitle(bookModel.getTitle());
        if (bookModel.getAuthor() != null) {
            book.setAuthor(authorMapper.toDao(bookModel.getAuthor()));
        }
        if (!CollectionUtils.isEmpty(bookModel.getGenres())) {
            book.setGenres((bookModel.getGenres().stream().map(genreMapper::toDao).toList()));
        }
        return book;
    }

    public String bookToString(BookModel book) {
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
