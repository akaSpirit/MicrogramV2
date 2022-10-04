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
public class CommentDto {
    private Long id;
    private String text;
    private LocalDateTime time;
    @JsonProperty("post_id")
    private Long postId;
    @JsonProperty("user_id")
    private Long userId;

    public CommentDto(String text, LocalDateTime time, Long postId, Long userId) {
        this.text = text;
        this.time = time;
        this.postId = postId;
        this.userId = userId;
    }
}
