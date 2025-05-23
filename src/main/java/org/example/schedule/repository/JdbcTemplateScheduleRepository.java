package org.example.schedule.repository;

import org.example.schedule.common.ScheduleNotFoundException;
import org.example.schedule.dto.AuthorResponseDto;
import org.example.schedule.dto.Paging;
import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule save(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("name", schedule.getName());
        parameters.put("author_id", schedule.getAuthorId());
        parameters.put("password", schedule.getPassword());
        parameters.put("contents", schedule.getContents());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("updated_at", schedule.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        schedule.setId(key.longValue());

        return schedule;
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(Paging paging) {
        int size = paging.getSize();

        return jdbcTemplate.query(
                """
                select a.id, a.name, a.password, a.contents, a.created_at, a.updated_at, a.author_id,
                       b.id, b.name, b.email, b.created_at, b.updated_at
                from schedule AS a
                INNER JOIN author AS b ON a.author_id = b.id
                order by a.updated_at desc LIMIT ? OFFSET ?
                """,
                scheduleRowMapper(), size, paging.offSet());
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        List<ScheduleResponseDto> result = jdbcTemplate.query(
                """
                        select a.id, a.name, a.password, a.contents, a.created_at, a.updated_at, a.author_id,
                                b.id, b.name, b.email, b.created_at, b.updated_at
                        from schedule AS a
                        INNER JOIN author AS b ON a.author_id = b.id         
                        where a.id = ?
                        """
                , scheduleRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ScheduleNotFoundException("id = " + id));
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByParams(String name, LocalDate updatedAt, Long authorId) {

        return jdbcTemplate.query(
                   """
                   select a.id, a.name, a.password, a.contents, a.created_at, a.updated_at, a.author_id,
                            b.id, b.name, b.email, b.created_at, b.updated_at
                    from schedule AS a
                    INNER JOIN author AS b ON a.author_id = b.id         
                    where (? is null or a.name = ?) 
                           and (? is null or date(a.updated_at) = ?)
                           and (? is null or a.author_id = ?)
                           order by a.updated_at desc 
                    """
                        , scheduleRowMapper(), name, name, updatedAt, updatedAt, authorId, authorId);
    }

    @Override
    public int updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        return jdbcTemplate.update("update schedule set contents = ?, name = ?, updated_at = ? where id = ? ", scheduleRequestDto.getContents(), scheduleRequestDto.getName(), LocalDateTime.now(), id);
    }

    @Override
    public int deleteSchedule(Long id, String password) {
        return jdbcTemplate.update("delete from schedule where id = ? and password = ?", id, password);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return (rs, rowNum) -> {
            // AuthorDto 생성
            AuthorResponseDto author = new AuthorResponseDto(
                    rs.getLong("b.id"),
                    rs.getString("b.email"),
                    rs.getString("b.name"),
                    rs.getTimestamp("b.created_at").toLocalDateTime(),
                    rs.getTimestamp("b.updated_at").toLocalDateTime()
            );

            // ScheduleResponseDto 생성 (AuthorResponseDto를 포함)
            return new ScheduleResponseDto(
                    rs.getLong("a.id"),
                    rs.getString("a.name"),
                    rs.getString("a.password"),
                    rs.getString("a.contents"),
                    rs.getTimestamp("a.created_at").toLocalDateTime(),
                    rs.getTimestamp("a.updated_at").toLocalDateTime(),
                    author
            );
        };
    }


}
