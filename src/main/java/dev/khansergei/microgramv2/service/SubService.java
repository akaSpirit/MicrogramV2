package dev.khansergei.microgramv2.service;

import dev.khansergei.microgramv2.dao.SubDao;
import dev.khansergei.microgramv2.dao.UserDao;
import dev.khansergei.microgramv2.dto.SubDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubService {
    private final SubDao subDao;
    private final UserDao userDao;

    public String subscribe(SubDto subDto, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "Login please";
        if (!userDao.ifExistsId(subDto.getUserId()))
            return "No such user";
        subDao.subscribe(subDto.getUserId(), userDao.getIdByUsername(username));
        return "Subscribed";
    }
}
