package com.hanjeokseoul.quietseoul.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String birthdate;
    private String gender;
    private String role;
}
