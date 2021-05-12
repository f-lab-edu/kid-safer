package com.flab.kidsafer.domain;

public class User {
    private int userId;
    private String password;
    private String email;
    private String nickname;
    private String phone;
    private String type;
    private String status;

    public User(int userId, String password, String email, String nickname, String phone, String type, String status) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.type = type;
        this.status = status;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}