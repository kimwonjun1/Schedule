package com.capstone.schedule.repository;

import com.capstone.schedule.dto.ScheduleResponseDto;
import com.capstone.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 일정 생성 로직
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id"); // 식별자 자동 생성

        // 파라미터 맵 구성
        Map<String, Object> params = new HashMap<>();
        params.put("work", schedule.getWork());
        params.put("password", schedule.getPassword());
        params.put("writer_id", schedule.getWriterId());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new org.springframework.jdbc.core.namedparam.MapSqlParameterSource(params));

        Schedule saved = findScheduleByIdOrElseThrow(key.longValue());
        return new ScheduleResponseDto(saved);
    }

    // 일정 전체 조회 로직
    @Override
    public List<Schedule> findAllSchedules(String modificationDate, String name, Long writerId) {
        String sql = "SELECT * FROM schedule WHERE 1=1"; // 동적 조건을 위한 기본 쿼리.(where 1=1 : 항상 참) 조건에 따라 where문에 쿼리 추가
        List<Object> params = new ArrayList<>();

        // 수정일로 조회하는 경우
        if (modificationDate != null) {
            sql += " AND DATE(modification_date) = ?";
            params.add(LocalDate.parse(modificationDate));
        }

        // 작성자명으로 조회하는 경우
        if (name != null) {
            sql += " AND writer_id IN (SELECT id FROM writer WHERE name = ?)";
            params.add(name);
        }

        // 작성자 Id로 조회하는 경우
        if (writerId != null) {
            sql += " AND writer_id = ?";
            params.add(writerId);
        }

        return jdbcTemplate.query(sql, scheduleRowMapper(), params.toArray()); // 최종 쿼리 실행 및 결과 매핑
    }

    // 일정 단건 조회 로직
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapper(), id);

        // 조회 결과가 없을 경우 예외 발생
        return result.stream().findAny().orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found: id = " + id));
    }

    // 일정(work) 수정 로직 (비밀번호 일치 조건 포함)
    @Override
    public int updateWork(Long id, String work, String password) {
        return jdbcTemplate.update(
                "UPDATE schedule SET work = ?, modification_date = CURRENT_TIMESTAMP WHERE id = ? AND password = ?",
                work, id, password
        );
    }

    // 일정 삭제 로직
    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    // Schedule 엔티티 객체로 매핑
    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("work"),
                rs.getString("password"),
                rs.getTimestamp("creation_date") != null ? rs.getTimestamp("creation_date").toLocalDateTime() : null,
                rs.getTimestamp("modification_date") != null ? rs.getTimestamp("modification_date").toLocalDateTime() : null,
                rs.getLong("writer_id")
        );
    }
}
