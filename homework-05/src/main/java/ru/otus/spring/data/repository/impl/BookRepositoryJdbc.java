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
import ru.otus.spring.data.domain.BookGenreRelation;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.data.repository.BookRepository;
import ru.otus.spring.data.repository.GenreRepository;
import ru.otus.spring.exception.InvalidOperationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class BookRepositoryJdbc implements BookRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    private final GenreRepository genreRepository;

    @Override
    public List<Book> findAll() {
        var books = getAllBooksWithoutGenres();
        var relations = getAllGenreRelations();
        var genres = genreRepository.findAll();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Optional<Book> findById(long id) {
        try {
            var book = getBookByIdWithoutGenres(id);
            var relations = getGenreRelationsByBookId(id);
            var genres = genreRepository.findAll();
            mergeBooksInfo(List.of(book), genres, relations);
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

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbcOperations.query("SELECT book_id, genre_id from books_genres", new BookGenreRelationRowMapper());
    }

    private List<BookGenreRelation> getGenreRelationsByBookId(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.query("""
                SELECT book_id, genre_id from books_genres
                WHERE book_id = :id
                """, params, new BookGenreRelationRowMapper());
    }

    private List<Book> getAllBooksWithoutGenres() {
        return jdbcOperations.query("""
                SELECT b.id, b.title, b.author_id, a.full_name
                FROM books as b 
                LEFT JOIN authors as a ON b.author_id = a.id
                """, new BookRowMapper());
    }

    private Book getBookByIdWithoutGenres(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject("""
                SELECT b.id, b.title, b.author_id, a.full_name
                FROM books as b 
                LEFT JOIN authors as a ON b.author_id = a.id
                WHERE b.id = :id
                """, params, new BookRowMapper());
    }

    private void mergeBooksInfo(List<Book> books, List<Genre> genres, List<BookGenreRelation> relations) {
        for (Book book : books) {
            List<Genre> bookGenre = findGenresForBook(book.getId(), relations, genres);
            book.setGenres(bookGenre);
        }
    }

    private List<Genre> findGenresForBook(Long bookId, List<BookGenreRelation> relations, List<Genre> genres) {
        List<Genre> bookGenres = new ArrayList<>();
        for (BookGenreRelation relation : relations) {
            if (relation.getBookId().equals(bookId)) {
                findGenreById(relation.getGenreId(), genres)
                        .ifPresent(bookGenres::add);
            }
        }
        return bookGenres;
    }

    private Optional<Genre> findGenreById(Long genreId, List<Genre> genres) {
        return genres.stream()
                .filter(it -> it.getId().equals(genreId))
                .findFirst();
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
        batchInsertGenresRelationsFor(book);
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

        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        var genreIds = book.getGenres().stream().map(Genre::getId).toList();
        String values = genreIds.stream()
                .map(i -> "(" + book.getId() + "," + i + ")")
                .collect(Collectors.joining(","));
        jdbcOperations
                .update("insert into books_genres(book_id, genre_id) values " + values, new HashMap<>());
    }

    private void removeGenresRelationsFor(Book book) {
        Map<String, Object> params = Collections.singletonMap("id", book.getId());
        jdbcOperations.update("DELETE FROM books_genres WHERE book_id = :id", params);
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var author = prepareAuthor(rs);
            return createBook(rs, author);
        }

        private Book createBook(ResultSet rs, Author author) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("title"),
                    author,
                    new ArrayList<>());
        }

        private Author prepareAuthor(ResultSet rs) throws SQLException {
            return new Author(
                    rs.getLong("author_id"),
                    rs.getString("full_name"));
        }
    }

    private static class BookGenreRelationRowMapper implements RowMapper<BookGenreRelation> {
        @Override
        public BookGenreRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new BookGenreRelation(
                    rs.getLong("book_id"),
                    rs.getLong("genre_id"));
        }
    }
}
