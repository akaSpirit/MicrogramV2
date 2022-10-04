package dev.khansergei.microgramv2.service;

import dev.khansergei.microgramv2.dao.CommentDao;
import dev.khansergei.microgramv2.dao.UserDao;
import dev.khansergei.microgramv2.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;
    private final UserDao userDao;

    public String addComment(CommentDto commentDto, Authentication auth) {
        String username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "comment not added";
        return commentDao.addComment(commentDto, userDao.getIdByUsername(username));
    }

    public String deleteComment(Long postID, Long commentID, Authentication auth) {
        String username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "comment not deleted";
        return commentDao.deleteComment(commentID, postID);
    }
}
