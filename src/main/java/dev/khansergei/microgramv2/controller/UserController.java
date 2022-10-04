package dev.khansergei.microgramv2.controller;

import dev.khansergei.microgramv2.dto.UserDto;
import dev.khansergei.microgramv2.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/username/{username}")
    public ResponseEntity<List<UserDto>> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<UserDto>> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/fullname/{fullname}")
    public ResponseEntity<List<UserDto>> getUserByName(@PathVariable String fullname) {
        return new ResponseEntity<>(userService.getUserByName(fullname), HttpStatus.OK);
    }

    @GetMapping("/{username}/subs")
    public ResponseEntity<List<UserDto>> getSubsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getSubsByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<String> existsEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.existsEmail(email), HttpStatus.OK);
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<List<UserDto>> getFollowersByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getFollowersByUsername(username), HttpStatus.OK);
    }
}
