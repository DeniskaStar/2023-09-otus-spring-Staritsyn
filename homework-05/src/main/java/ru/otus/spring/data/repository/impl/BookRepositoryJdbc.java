package ru.otus.spring.data.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.domain.Book;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.data.repository.BookRepository;
import ru.otus.spring.data.repository.extractor.book.BookGenreRelation;
import ru.otus.spring.data.repository.extractor.book.BookGenreResultSetExtractor;
import ru.otus.spring.exception.InvalidOperationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BookRepositoryJdbc implements BookRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Book> findAll() {
        var books = getAllBooksWithoutGenres();
        loadGenres(books);
        return books;
    }

    @Override
    public Optional<Book> findById(long id) {
        try {
            var book = getBookByIdWithoutGenres(id);
            loadGenres(List.of(book));
            return Optional.of(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new InvalidOperationException(e);
        }
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbcOperations.update("DELETE FROM books WHERE id = :id", params);
    }

    private List<Book> getAllBooksWithoutGenres() {
        return jdbcOperations.query("""
                SELECT b.id, b.title, a.id author_id, a.full_name author_full_name
                FROM books as b
                LEFT JOIN authors as a ON b.author_id = a.id
                """, new BookRowMapper());
    }

    private void loadGenres(List<Book> books) {
        var bookGenreRelations = findBookGenreRelation();
        mergeBooksInfo(books, bookGenreRelations);
    }

    private List<BookGenreRelation> findBookGenreRelation() {
        return jdbcOperations.query("""
                SELECT bg.book_id, bg.genre_id, g.name genre_name
                FROM books_genres as bg
                LEFT JOIN genres as g ON bg.genre_id = g.id
                """, new BookGenreResultSetExtractor());
    }

    private void mergeBooksInfo(List<Book> books, List<BookGenreRelation> bookGenreRelations) {
        for (Book book : books) {
            bookGenreRelations.stream()
                    .filter(it -> it.getBookId().equals(book.getId()))
                    .findFirst()
                    .ifPresent(it -> book.setGenres(it.getGenres()));
        }
    }

    private Book getBookByIdWithoutGenres(long id) {
        return jdbcOperations.queryForObject("""
                SELECT b.id book_id, b.title book_title,
                b.author_id author_id, a.full_name author_full_name,
                FROM books as b
                LEFT JOIN authors as a ON b.author_id = a.id
                WHERE b.id = :id
                """, Map.of("id", id), new BookRowMapper());
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId());

        jdbcOperations.update("""
                INSERT INTO books (title, author_id) VALUES (:title, :author_id)
                """, parameters, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Long.class));
        saveGenres(book);
        return book;
    }

    private Book update(Book book) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId());

        jdbcOperations.update("""
                UPDATE books SET title = :title, author_id = :author_id WHERE id =:id
                """, parameters);

        deleteGenres(book);
        saveGenres(book);
        return book;
    }

    private void saveGenres(Book book) {
        List<MapSqlParameterSource> batchParams = new ArrayList<>();
        for (Genre genre : book.getGenres()) {
            MapSqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("book_id", book.getId())
                    .addValue("genre_id", genre.getId());
            batchParams.add(parameters);
        }
        jdbcOperations.batchUpdate("""
                INSERT INTO books_genres (book_id, genre_id)
                VALUES (:book_id, :genre_id)
                """, batchParams.toArray(new MapSqlParameterSource[0]));
    }

    private void deleteGenres(Book book) {
        jdbcOperations.update("DELETE FROM books_genres WHERE book_id = :id", Map.of("id", book.getId()));
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var author = createAuthor(rs);
            return createBook(rs, author);
        }

        private Book createBook(ResultSet rs, Author author) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("title"),
                    author,
                    new ArrayList<>());
        }

        private Author createAuthor(ResultSet rs) throws SQLException {
            return new Author(
                    rs.getLong("author_id"),
                    rs.getString("full_name"));
        }
    }
}
