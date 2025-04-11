package com.hanjeokseoul.quietseoul.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String name;
    private String password;
    private String phone;
    private String birthdate;
    private String gender;
}
