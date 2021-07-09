package com.flab.kidsafer.domain;

import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.enums.UserType;
import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private int userId;
    private String password;
    private String email;
    private String nickname;
    private String phone;
    private UserType type;
    private Status status;
    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGeneratedAt;

    public User(int userId, String password, String email, String nickname, String phone,
        UserType type,
        Status status) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.type = type;
        this.status = status;
    }

    public User(User.Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.nickname = builder.nickname;
        this.phone = builder.phone;
        this.type = builder.type;
        this.status = builder.status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmailCheckToken() {
        return this.emailCheckToken;
    }

    public LocalDateTime getEmailCheckTokenGeneratedAt() {
        return this.emailCheckTokenGeneratedAt;
    }

    public static class Builder {

        private String email;
        private String password;
        private String nickname;
        private String phone;
        private UserType type;
        private Status status;

        public Builder(String email, String password, UserType type) {
            this.email = email;
            this.password = password;
            this.type = type;
        }

        public User.Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public User.Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public User.Builder status(Status status) {
            this.status = status;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        this.status = Status.DEFAULT;
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1));
    }
}
