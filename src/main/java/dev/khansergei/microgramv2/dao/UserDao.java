package dev.khansergei.microgramv2.dao;

import dev.khansergei.microgramv2.dto.UserDto;
import dev.khansergei.microgramv2.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final String temp = "select u.id, u.username, u.fullname, u.email, u.password, " +
                                "(select count(id) from posts p where p.user_id = u.id) posts, " +
                                "(select count(id) from subs s where s.follower_id = u.id) followers, " +
                                "(select count(id) from subs s where s.user_id = u.id) following";

    public void dropTable() {
        String sql = "drop table if exists users";
        jdbcTemplate.execute(sql);
    }

    public void dropTableAuth() {
        String sql = "drop table if exists authorities";
        jdbcTemplate.execute(sql);
    }

    public void createTableUsers() {
        String sql = "create table users ( " +
                        "id bigserial primary key not null, " +
                        "fullname varchar(50) not null, " +
                        "username varchar(50) not null, " +
                        "email varchar(50) not null, " +
                        "password varchar not null," +
                        "enabled boolean not null);";
        jdbcTemplate.update(sql);
    }

    public void createTableAuth() {
        String sql = "create table authorities ( " +
                    "id bigserial primary key not null, " +
                    "user_id integer not null references users (id), " +
                    "authority text not null);";
        jdbcTemplate.update(sql);
    }
    
    public List<UserDto> getUserByName(String fullname) {
        String sql = temp + "from users u where u.fullname = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), fullname);
    }

    public List<UserDto> getUserByUsername(String username) {
        String sql = temp + "from users u where u.username = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), username);
    }

    public List<UserDto> getUserByEmail(String email) {
        String sql =  temp + "from users u where u.email = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), email);
    }

    public boolean existsEmail(String email) {
        String sql = "select count(id) from users " +
                "where email = ?";
        var result = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return result == 1;
    }

    public Long getIdByUsername(String username) {
        String sql = "select id from users where username = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, username);
    }

    public List<UserDto> getFollowersByUsername(String username) {
        String sql = temp + "from subs s inner join users u on u.id = s.follower_id " +
                "where s.user_id = ( select id from users where username = ? );";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), username);
    }

    public List<UserDto> getSubsByUsername(String username) {
        String sql = temp + "from subs s inner join users u on u.id = s.user_id " +
                "where s.follower_id = ( select id from users where username = ? );";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDto.class), username);
    }

    public String addUser(User user) {
        if (!ifExists(user)) {
            String sql = "insert into users(username, fullname, email, password, enabled) values(?, ?, ?, ?, true)";
            var sm = jdbcTemplate.update(sql, user.getUsername(), user.getFullname(), user.getEmail(), encoder.encode(user.getPassword()));
            createAuthority(user.getUsername());
            return "Success";
        }
        return "Try again";
    }

    private void createAuthority(String username) {
        var userId = getIdByUsername(username);
        String sql = "insert into authorities(user_id, authority) values(?, 'USER')";
        jdbcTemplate.update(sql, userId);
    }

    public boolean login(User user) {
        var password = encoder.encode(user.getPassword());
        if (ifExistsUsername(user.getUsername())) {
            var userDto = getUserByUsername(user.getUsername()).get(0);
            if (userDto.getPassword().equals(password))
                return true;
        }
        return false;
    }

    public boolean ifExistsId(Long id) {
        String sql = "select count(*) from users where id = ?";
        var count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count == 1;
    }

    private boolean ifExists(User user) {
        if (existsEmail(user.getEmail()))
            return true;
        if (ifExistsUsername(user.getUsername()))
            return true;
        return false;
    }

    public boolean ifExistsUsername(String username) {
        String sql = "select count(id) from users " +
                "where username = ?";
        var result = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return result == 1;
    }

    public void addData(List<UserDto> users) {
        String sql = "insert into users(username, fullname, email, password, enabled) values(?, ?, ?, ?, ?)";
        String sqlAuth = "insert into authorities(user_id, authority) values(?, ?)";
        for (int i = 0; i < users.size(); i++) {
            int finalI = i;
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, users.get(finalI).getUsername());
                ps.setString(2, users.get(finalI).getFullname());
                ps.setString(3, users.get(finalI).getEmail());
                ps.setString(4, encoder.encode(users.get(finalI).getPassword()));
                ps.setBoolean(5, users.get(finalI).isEnabled());
                return ps;
            });
            jdbcTemplate.update(sqlAuth, i + 1, "USER");
        }
    }
}
