package com.flab.kidsafer.domain;

import com.flab.kidsafer.domain.enums.SignInStatus;

public class SignInResponse {

    private SignInStatus status;
    private User user;

    public SignInResponse(SignInStatus status, User user) {
        this.status = status;
        this.user = user;
    }

    public SignInStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }
}
