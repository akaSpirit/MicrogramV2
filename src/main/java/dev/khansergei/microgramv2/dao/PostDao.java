package dev.khansergei.microgramv2.dao;

import dev.khansergei.microgramv2.dto.PostDto;
import dev.khansergei.microgramv2.dto.PostImageDto;
import dev.khansergei.microgramv2.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public void dropTable() {
        String sql = "drop table if exists posts";
        jdbcTemplate.execute(sql);
    }

    public void createTable() {
        String sql = "create table posts( " +
                "id bigserial primary key not null, " +
                "image text not null, " +
                "description text default 'default description', " +
                "time timestamp without time zone not null, " +
                "user_id integer not null REFERENCES users (id) );";
        jdbcTemplate.update(sql);
    }

    private void deleteComments(Long postId) {
        String sql = "delete from comments where post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    private void deleteLikes(Long postId) {
        String sql = "delete from likes where post_id = ?";
        jdbcTemplate.update(sql, postId);
    }

    public void deletePost(Long postId, String username) {
        deleteComments(postId);
        deleteLikes(postId);
        var image = getImageById(postId);
        Util.deleteFile(image.get());
        String sql = "delete from posts " +
                "where id = ? and user_id = ?";
        jdbcTemplate.update(sql, postId, getUserIdByUsername(username));
    }

    public List<PostDto> getPostsByUsername(String username) {
        String sql = "select p.id, p.image, p.description, p.time, " +
                "(select count(id) " +
                "from comments c where c.post_id = p.id) comments " +
                "from posts p " +
                "inner join users u on p.user_id = u.id " +
                "where u.username = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PostDto.class), username);
    }

    public List<PostDto> getFeedByUsername(String username) {
        String sql = "select p.id, p.image, p.description, p.time, (select count(id) from comments c where c.post_id = p.id) comments " +
                "from posts p " +
                "inner join subs s on s.user_id = p.user_id " +
                "inner join users u on u.id = s.follower_id " +
                "where u.username = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PostDto.class), username);
    }

    public Integer getAmountOfPostsByUsername(String username) {
        var list = getPostsByUsername(username);
        return list.size();
    }

    public PostDto addPost(PostImageDto post) {
        LocalDateTime ldt = LocalDateTime.now();
        String sql = "insert into posts(image, description, time, user_id) values(?, ?, ?, ?)";
        String fileName = Util.createFile(
                post.getImageFile(),
                getAmountOfPostsByUsername(post.getUsername()) + 1,
                post.getUsername()
        );
        jdbcTemplate.update(sql, fileName, post.getDescription(), Timestamp.valueOf(ldt), getUserIdByUsername(post.getUsername()));

        return PostDto.builder()
                .id(getIdByImage(fileName))
                .image(fileName)
                .description(post.getDescription())
                .time(ldt)
                .comments(0)
                .build();
    }

    private Optional<String> getImageById(Long postId) {
        String sql = "select image from posts where id = ?";
        String image = jdbcTemplate.queryForObject(sql, String.class, postId);
        return Optional.of(image);
    }

    private Long getIdByImage(String image) {
        String sql = "select id from posts where image = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, image);
    }

    private Long getUserIdByUsername(String username) {
        String sql = "select id from users where username = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, username);
    }

    public void addData(List<PostDto> posts) {
        String sql = "insert into posts(image, description, time, user_id) values(?, ?, ?, ?)";
        for (PostDto p : posts) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, p.getImage());
                ps.setString(2, p.getDescription());
                ps.setTimestamp(3, Timestamp.valueOf(p.getTime()));
                ps.setInt(4, p.getUserId().intValue());
                return ps;
            });
        }
    }
}
