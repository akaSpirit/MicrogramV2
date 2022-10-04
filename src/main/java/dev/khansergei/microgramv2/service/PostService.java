package dev.khansergei.microgramv2.service;

import dev.khansergei.microgramv2.dao.PostDao;
import dev.khansergei.microgramv2.dao.UserDao;
import dev.khansergei.microgramv2.dto.PostDto;
import dev.khansergei.microgramv2.dto.PostImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostDao postDao;
    private final UserDao userDao;

    public List<PostDto> getPostsByUsername(String username) {
        return postDao.getPostsByUsername(username);
    }

    public List<PostDto> getFeedByUsername(String username) {
        return postDao.getFeedByUsername(username);
    }

    public Optional<?> addPost(MultipartFile image, String description, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return Optional.empty();
        return Optional.of(postDao.addPost(
                PostImageDto.builder()
                        .imageFile(image)
                        .description(description)
                        .username(username)
                        .build()));
    }

    public String deletePost(Long postId, Authentication auth) {
        var username = auth.getName();
        if (!userDao.ifExistsUsername(username))
            return "Post not deleted";
        postDao.deletePost(postId, username);
        return "Post deleted";
    }
}
