package dev.khansergei.microgramv2.service;

import dev.khansergei.microgramv2.dao.UserDao;
import dev.khansergei.microgramv2.dto.UserDto;
import dev.khansergei.microgramv2.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public List<UserDto> getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public List<UserDto> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public List<UserDto> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public String existsEmail(String email) {
        if (userDao.existsEmail(email)) {
            return "Signed in";
        }
        return "User not signed yet";
    }

    public String addUser(UserDto userDto) {
        if (userDto.getUsername() == null)
            return "Username cannot be null";
        if (userDto.getFullname() == null)
            return "Fullname cannot be null";
        if (userDto.getEmail() == null)
            return "Email cannot be null";
        if (userDto.getPassword() == null)
            return "Password cannot be null";
        return userDao.addUser(
                User.builder()
                        .username(userDto.getUsername())
                        .fullname(userDto.getFullname())
                        .email(userDto.getEmail())
                        .password(userDto.getPassword())
                        .build());
    }

    public List<UserDto> getFollowersByUsername(String username) {
        return userDao.getFollowersByUsername(username);
    }

    public List<UserDto> getSubsByUsername(String username) {
        return userDao.getSubsByUsername(username);
    }
}
