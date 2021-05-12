package com.flab.kidsafer.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotEmpty
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotEmpty
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
