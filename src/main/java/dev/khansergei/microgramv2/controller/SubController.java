package dev.khansergei.microgramv2.controller;

import dev.khansergei.microgramv2.dto.SubDto;
import dev.khansergei.microgramv2.service.SubService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subs")
@RequiredArgsConstructor
public class SubController {
    private final SubService subService;

    @PostMapping
    public ResponseEntity<String> subscribe(@RequestBody SubDto subDto, Authentication auth) {
        return new ResponseEntity<>(subService.subscribe(subDto, auth), HttpStatus.OK);
    }
}
