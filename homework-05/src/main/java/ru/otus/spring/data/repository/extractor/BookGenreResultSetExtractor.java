package ru.otus.spring.data.repository.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.data.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookGenreResultSetExtractor implements ResultSetExtractor<Map<Long, List<Genre>>> {

    @Override
    public Map<Long, List<Genre>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, List<Genre>> bookGenreRelations = new HashMap<>();
        while (rs.next()) {
            var bookId = rs.getLong("book_id");
            var genres = bookGenreRelations.computeIfAbsent(bookId, k -> new ArrayList<>());
            var genre = new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
            genres.add(genre);
        }

        return bookGenreRelations;
    }
}
