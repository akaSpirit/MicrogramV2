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
public class LikeDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("post_id")
    private Long postId;
    private LocalDateTime time;

    public LikeDto(Long userId, Long postId, LocalDateTime time) {
        this.userId = userId;
        this.postId = postId;
        this.time = time;
    }
}
