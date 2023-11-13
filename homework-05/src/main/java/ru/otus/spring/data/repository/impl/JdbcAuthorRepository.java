package ru.otus.spring.data.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.data.domain.Author;
import ru.otus.spring.data.repository.AuthorRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Author> findAll() {
        return jdbcOperations.query("SELECT id, full_name FROM authors", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        try {
            var author = jdbcOperations.queryForObject("""
                    SELECT id, full_name FROM authors WHERE id = :id
                    """, Map.of("id", id), new AuthorRowMapper());
            return Optional.ofNullable(author);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            return insert(author);
        }
        return update(author);
    }

    private Author insert(Author author) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("full_name", author.getFullName());

        jdbcOperations.update("""
                INSERT INTO authors (full_name) VALUES (:full_name)
                """, parameters, keyHolder, new String[]{"id"});

        author.setId(keyHolder.getKeyAs(Long.class));
        return author;
    }

    private Author update(Author author) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", author.getId())
                .addValue("full_name", author.getFullName());

        jdbcOperations.update("""
                UPDATE authors SET full_name = :full_name WHERE id =:id
                """, parameters);

        return author;
    }

    private static class AuthorRowMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("id");
            var fullName = rs.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
