package ru.otus.spring.data.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.data.domain.Genre;
import ru.otus.spring.data.repository.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Genre> findAll() {
        return jdbcOperations.query("SELECT id, name FROM genres", new GenreRowMapper());
    }

    @Override
    public List<Genre> findByIds(Collection<Long> genreIds) {
        Map<String, Object> params = Collections.singletonMap("ids", genreIds);
        return jdbcOperations.query("SELECT id, name FROM genres WHERE id IN (:ids)", params, new GenreRowMapper());
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            return insert(genre);
        }
        return update(genre);
    }

    private Genre insert(Genre genre) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", genre.getName());

        jdbcOperations.update("""
                INSERT INTO genres (name) VALUES (:name)
                """, parameters, keyHolder, new String[]{"id"});

        genre.setId(keyHolder.getKeyAs(Long.class));
        return genre;
    }

    private Genre update(Genre genre) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", genre.getId())
                .addValue("name", genre.getName());

        jdbcOperations.update("""
                UPDATE genres SET name = :name WHERE id =:id
                """, parameters);

        return genre;
    }

    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("id");
            var name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
