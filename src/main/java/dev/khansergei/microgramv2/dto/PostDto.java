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
public class PostDto {
    private Long id;
    private String image;
    private String description;
    private LocalDateTime time;
    private Integer comments;
    @JsonProperty("user_id")
    private Long userId;

    public PostDto(String image, String description, LocalDateTime time, Long userId) {
        this.image = image;
        this.description = description;
        this.time = time;
        this.userId = userId;
    }
}
