package com.flab.kidsafer.config.auth.dto;

import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.User;
import java.io.Serializable;

public class SessionUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String type;
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

    public String getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }
}
