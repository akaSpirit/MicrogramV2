package dev.khansergei.microgramv2.utils;

import dev.khansergei.microgramv2.dto.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import dev.khansergei.microgramv2.dao.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitDB {

    @Bean
    public String init(UserDao userDao, PostDao postDao, CommentDao commentDao, LikeDao likeDao, SubDao subDao) {
        subDao.dropTable();
        likeDao.dropTable();
        commentDao.dropTable();
        postDao.dropTable();
        userDao.dropTableAuth();
        userDao.dropTable();

        userDao.createTableUsers();
        userDao.createTableAuth();
        postDao.createTable();
        commentDao.createTable();
        likeDao.createTable();
        subDao.createTable();

        userDao.addData(addUsers());
        postDao.addData(addPosts());
        commentDao.addData(addComments());
        likeDao.addData(addLikes());
        subDao.addData(addSubs());
        return "init...";
    }

    private List<UserDto> addUsers() {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto("username1", "name1", "email1@email", "qwe1", true));
        users.add(new UserDto("username2", "name2", "email2@email", "qwe2", true));
        users.add(new UserDto("username3", "name3", "email3@email", "qwe3", true));
        users.add(new UserDto("username4", "name4", "email4@email", "qwe4", true));
        users.add(new UserDto("username5", "name5", "email5@email", "qwe5", true));
        return users;
    }

    private List<PostDto> addPosts() {
        List<PostDto> posts = new ArrayList<>();
        posts.add(new PostDto("test.jpg", "description1", LocalDateTime.now(), 1L));
        posts.add(new PostDto("test.jpg", "description2", LocalDateTime.now(), 2L));
        posts.add(new PostDto("test.jpg", "description3", LocalDateTime.now(), 3L));
        posts.add(new PostDto("test.jpg", "description4", LocalDateTime.now(), 4L));
        posts.add(new PostDto("test.jpg", "description5", LocalDateTime.now(), 5L));
        return posts;
    }

    private List<CommentDto> addComments() {
        List<CommentDto> comments = new ArrayList<>();
        comments.add(new CommentDto("comment1", LocalDateTime.now(), 5L, 1L));
        comments.add(new CommentDto("comment2", LocalDateTime.now(),1L,2L));
        comments.add(new CommentDto("comment3", LocalDateTime.now(),2L, 3L));
        comments.add(new CommentDto("comment4", LocalDateTime.now(), 3L, 4L));
        comments.add(new CommentDto("comment5", LocalDateTime.now(),4L, 5L));
        return comments;
    }

    private List<LikeDto> addLikes() {
        List<LikeDto> likes = new ArrayList<>();
        likes.add(new LikeDto(1L, 2L, LocalDateTime.now()));
        likes.add(new LikeDto(2L, 3L, LocalDateTime.now()));
        likes.add(new LikeDto(3L, 4L, LocalDateTime.now()));
        likes.add(new LikeDto(4L, 5L, LocalDateTime.now()));
        likes.add(new LikeDto(5L, 1L, LocalDateTime.now()));
        return likes;
    }

    private List<SubDto> addSubs() {
        List<SubDto> subs = new ArrayList<>();
        subs.add(new SubDto(1L, 2L, LocalDateTime.now()));
        subs.add(new SubDto(2L, 3L, LocalDateTime.now()));
        subs.add(new SubDto(3L, 4L, LocalDateTime.now()));
        subs.add(new SubDto(4L, 5L, LocalDateTime.now()));
        subs.add(new SubDto(5L, 1L, LocalDateTime.now()));
        return subs;
    }
}


