package com.flab.kidsafer.config.auth.dto;

import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.UserType;
import java.io.Serializable;

public class SessionUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private UserType type;
    private Status status;

    public SessionUser(User user) {
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.type = user.getType();
        this.status = user.getStatus();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserType getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }
}
