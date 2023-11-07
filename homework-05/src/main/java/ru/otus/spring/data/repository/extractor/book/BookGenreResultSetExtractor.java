package ru.otus.spring.data.repository.extractor.book;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.data.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookGenreResultSetExtractor implements ResultSetExtractor<List<BookGenreRelation>> {

    @Override
    public List<BookGenreRelation> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, BookGenreRelation> bookGenreRelations = new HashMap<>();
        while (rs.next()) {
            var bookId = rs.getLong("book_id");
            var bookGenreRelation = bookGenreRelations.get(bookId);
            if (bookGenreRelation == null) {
                bookGenreRelation = new BookGenreRelation(bookId, new ArrayList<>());
                bookGenreRelations.put(bookId, bookGenreRelation);
            }

            bookGenreRelation.getGenres().add(new Genre(
                    rs.getLong("genre_id"),
                    rs.getString("genre_name")));
        }

        return bookGenreRelations.values().stream().toList();
    }
}
