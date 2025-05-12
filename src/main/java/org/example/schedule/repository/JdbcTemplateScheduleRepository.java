package org.example.schedule.repository;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

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
        parameters.put("password", schedule.getPassword());
        parameters.put("contents", schedule.getContents());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("updated_at", schedule.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        schedule.setId(key.longValue());

        return schedule;
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate.query("select * from schedule order by updated_at desc", scheduleRowMapper());
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        List<ScheduleResponseDto> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapper(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = ? " + id));
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByParams(String name, LocalDate updatedAt) {

        return jdbcTemplate.query("select * from schedule where (? is null or name = ?) and (? is null or date(updated_at) = ?) order by updated_at desc", scheduleRowMapper(), name, name, updatedAt, updatedAt);
    }

    @Override
    public int updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        return jdbcTemplate.update("update schedule set contents = ?, name = ?, updated_at = ? where id = ? ", scheduleRequestDto.getContents(), scheduleRequestDto.getName(), LocalDateTime.now(), id);
    }

    @Override
    public int deleteSchedule(Long id, String password) {
        return jdbcTemplate.update("delete from schedule where id = ? and password = ?", id, password);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("contents"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }


}
