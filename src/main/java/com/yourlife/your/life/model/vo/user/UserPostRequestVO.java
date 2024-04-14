package com.yourlife.your.life.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequestVO {
    private String id;
    private String name;
    private String email;
    private String password;
}
