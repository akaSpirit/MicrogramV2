package dev.khansergei.microgramv2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_nickname")
    private String userNickname;
    @JsonProperty("follower_id")
    private Long followerId;
    private LocalDateTime time;

    public SubDto(Long userId, Long followerId, LocalDateTime time) {
        this.userId = userId;
        this.followerId = followerId;
        this.time = time;
    }
}
