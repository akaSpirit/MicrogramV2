package dev.khansergei.microgramv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUserDto {
    private Long id;
    private String image;
    private String description;
    private String nickname;
}
