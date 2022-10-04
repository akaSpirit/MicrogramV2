package dev.khansergei.microgramv2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sub {
    private User follower;
    private User subscribeTo;
    private LocalDateTime subDateTime;
}
