package dev.khansergei.microgramv2.dao;

import dev.khansergei.microgramv2.dto.LikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeDao {
    private final JdbcTemplate jdbcTemplate;

    public void dropTable() {
        String sql = "drop table if exists likes";
        jdbcTemplate.execute(sql);
    }

    public void createTable() {
        String sql = "create table likes( " +
                "id bigserial primary key not null, " +
                "user_id integer not null references users (id), " +
                "post_id integer not null references posts (id), " +
                "time timestamp without time zone not null);";
        jdbcTemplate.update(sql);
    }

    public void likePost(LikeDto likeDto) {
        String sql = "insert into likes(user_id, post_id, time) values (?, ?, ?);";
        jdbcTemplate.update(sql, likeDto.getUserId(), likeDto.getPostId(), LocalDateTime.now());
    }

    public boolean checkIsLiked(Long userID, Long postID) {
        String sql = "select count(*) from likes where user_id = ? and post_id = ?";
        var count = jdbcTemplate.queryForObject(sql, Integer.class, userID, postID);
        return count == 1;
    }

    public void addData(List<LikeDto> likes) {
        String sql = "insert into likes(user_id, post_id, time) values (?, ?, ?);";
        for (LikeDto l : likes) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, l.getUserId().intValue());
                ps.setInt(2, l.getPostId().intValue());
                ps.setTimestamp(3, Timestamp.valueOf(l.getTime()));
                return ps;
            });
        }
    }

}
