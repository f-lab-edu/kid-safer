package com.flab.kidsafer.event;

public class UserStatusEvent {

    private int userId;
    private String message;

    public UserStatusEvent(int userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public UserStatusEvent(Builder builder) {
        this.userId = builder.userId;
        this.message = builder.message;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder {

        private int userId;
        private String message;

        public Builder() {
        }

        public UserStatusEvent.Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public UserStatusEvent.Builder message(String message) {
            this.message = message;
            return this;
        }

        public UserStatusEvent build() {
            return new UserStatusEvent(this);
        }
    }
}
