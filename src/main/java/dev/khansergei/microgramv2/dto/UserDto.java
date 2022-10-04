package dev.khansergei.microgramv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String fullname;
    private String username;
    private String email;
    private String password;
    private int posts;
    private int following;
    private int followers;
    private boolean enabled;

    public UserDto(String username, String fullname, String email, String password, boolean enabled) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }
}
