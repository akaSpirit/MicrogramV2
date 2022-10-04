package dev.khansergei.microgramv2.service;

import dev.khansergei.microgramv2.dao.LikeDao;
import dev.khansergei.microgramv2.dao.PostDao;
import dev.khansergei.microgramv2.dao.UserDao;
import dev.khansergei.microgramv2.dto.LikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDao likeDao;
    private final UserDao userDao;

    public String checkLike(Long userID, Long postID) {
        if (likeDao.checkIsLiked(userID, postID))
            return "Liked yet";
        return "Not liked yet";
    }
    public String likePost(LikeDto likeDto, Authentication auth) {
        String username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "Login please";
        if (likeDao.checkIsLiked(userDao.getIdByUsername(username), likeDto.getPostId()))
            return "Already liked";
        likeDto.setUserId(userDao.getIdByUsername(username));
        likeDao.likePost(likeDto);
        return "Liked post";
    }
}
