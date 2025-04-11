package com.hanjeokseoul.quietseoul.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String name;
    private String phone;
    private String birthdate;
    private String gender;
}
