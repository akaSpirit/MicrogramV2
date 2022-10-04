package dev.khansergei.microgramv2.controller;

import dev.khansergei.microgramv2.dto.CommentDto;
import dev.khansergei.microgramv2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{post_id}")
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto, @PathVariable Long post_id, Authentication auth) {
        commentDto.setPostId(post_id);
        return new ResponseEntity<>(commentService.addComment(commentDto, auth), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}&{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, Authentication auth) {
        return new ResponseEntity<>(commentService.deleteComment(post_id, comment_id, auth), HttpStatus.OK);
    }

}
