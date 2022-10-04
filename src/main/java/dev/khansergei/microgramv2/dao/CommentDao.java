package dev.khansergei.microgramv2.dao;

import dev.khansergei.microgramv2.dto.CommentDto;
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
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;

    public void dropTable() {
        String sql = "drop table if exists comments";
        jdbcTemplate.execute(sql);
    }

    public void createTable() {
        String sql = "create table comments " +
                "(id bigserial primary key not null, " +
                "post_id integer not null references posts (id), " +
                "user_id integer not null references users (id), " +
                "text text not null, " +
                "time timestamp without time zone not null);";
        jdbcTemplate.update(sql);
    }

    public String addComment(CommentDto commentDto, Long userId) {
        LocalDateTime ldt = LocalDateTime.now();
        String sql = "insert into comments(post_id, user_id, text, time) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, commentDto.getPostId(), userId, commentDto.getText(), ldt);
        return "Comment added";
    }

    private boolean exists(Long commentId, Long postId) {
        String sql = "select count(id) from comments WHERE id = ? AND post_id = ?";
        var count = jdbcTemplate.queryForObject(sql, Integer.class, commentId, postId);
        return count == 1;
    }

    public String deleteComment(Long commentId, Long postId) {
        if (exists(commentId, postId)) {
            String sql = "delete from comments where id = ?";
            jdbcTemplate.update(sql, commentId);
            return "comment deleted";
        }
        return "comment not deleted";
    }

    public void addData(List<CommentDto> comments) {
        String sql = "insert into comments(post_id, user_id, text, time) values(?, ?, ?, ?)";
        for (CommentDto c : comments) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, c.getPostId().intValue());
                ps.setInt(2, c.getUserId().intValue());
                ps.setString(3, c.getText());
                ps.setTimestamp(4, Timestamp.valueOf(c.getTime()));
                return ps;
            });
        }
    }
}
