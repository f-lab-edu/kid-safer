package com.flab.kidsafer.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class SignInRequest {

    /*
     * @Email :
     *  - LoginRequest 객체에 email 변수에 값을 부여할 때, 이메일형식(..@..)의 string 값인지 검증합니다.
     *  - 값이 유효하지 않을 경우, MethodArgumentNotValidException 예외를 발생시킵니다.
     */
    @NotEmpty
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotEmpty
    private String password;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignInRequest(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {

        private String email;
        private String password;

        public Builder(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public SignInRequest build() {
            return new SignInRequest(this);
        }
    }
}
