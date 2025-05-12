package org.example.schedule.repository;

import org.example.schedule.entity.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAuthorRepository (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Author save(Author author) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("author").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("email", author.getEmail());
        parameters.put("name", author.getName());
        parameters.put("created_at", author.getCreatedAt());
        parameters.put("updated_at", author.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        author.setId(key.longValue());

        return author;
    }
}
