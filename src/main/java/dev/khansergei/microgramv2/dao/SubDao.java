package dev.khansergei.microgramv2.dao;

import dev.khansergei.microgramv2.dto.SubDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubDao {
    private final JdbcTemplate jdbcTemplate;

    public void dropTable() {
        String sql = "drop table if exists subs";
        jdbcTemplate.execute(sql);
    }

    public void createTable() {
        String sql = "create table subs ( " +
                "id bigserial primary key not null, " +
                "user_id integer not null references users (id), " +
                "follower_id integer not null references users (id), " +
                "time timestamp without time zone not null " +
                ") ";
        jdbcTemplate.update(sql);
    }

    public void subscribe(Long userId, Long followerId) {
        String sql = "insert into subs(user_id, follower_id, time) values(?, ?, ?)";
        jdbcTemplate.update(sql, userId, followerId, LocalDateTime.now());
    }

    public void addData(List<SubDto> subs) {
        String sql = "insert into subs(user_id, follower_id, time) values(?, ?, ?)";
        for (SubDto s : subs) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, s.getUserId().intValue());
                ps.setInt(2, s.getFollowerId().intValue());
                ps.setTimestamp(3, Timestamp.valueOf(s.getTime()));
                return ps;
            });
        }
    }
}
