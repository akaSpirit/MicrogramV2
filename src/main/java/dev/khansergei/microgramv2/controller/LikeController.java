package dev.khansergei.microgramv2.controller;

import dev.khansergei.microgramv2.dto.LikeDto;
import dev.khansergei.microgramv2.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> likePost(@RequestBody LikeDto likeDto, Authentication auth) {
        return new ResponseEntity<>(likeService.likePost(likeDto, auth), HttpStatus.OK);
    }

    @GetMapping("/{user_id}&{post_id}")
    public ResponseEntity<String> checkLike(@PathVariable Long user_id, @PathVariable Long post_id) {
        return new ResponseEntity<>(likeService.checkLike(user_id, post_id), HttpStatus.OK);
    }
}
