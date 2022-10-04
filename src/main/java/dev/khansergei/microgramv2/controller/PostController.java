package dev.khansergei.microgramv2.controller;

import dev.khansergei.microgramv2.dto.PostDto;
import dev.khansergei.microgramv2.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{username}")
    public ResponseEntity<List<PostDto>> getPostsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(postService.getPostsByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/{username}/feed")
    public ResponseEntity<List<PostDto>> getFeedByUsername(@PathVariable String username) {
        return new ResponseEntity<>(postService.getFeedByUsername(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addPost(@RequestBody MultipartFile image, String description, Authentication auth) {
        var post = postService.addPost(image, description, auth);
        return post.isPresent() ?
                new ResponseEntity<>(post.get(), HttpStatus.OK) :
                new ResponseEntity<>("Login please for adding posts", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable Long post_id, Authentication auth) {
        return new ResponseEntity<>(postService.deletePost(post_id, auth), HttpStatus.OK);
    }
}
