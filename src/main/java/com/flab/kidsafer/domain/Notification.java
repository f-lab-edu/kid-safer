package com.flab.kidsafer.domain;

import com.flab.kidsafer.domain.enums.NotificationType;
import java.time.LocalDateTime;
import java.util.Objects;

public class Notification {

    private Long id;
    private String message;
    private boolean checked;
    private Long userId;

    private LocalDateTime createdDateTime;

    private NotificationType notificationType;

    public Notification(Long id, String message, boolean checked, Long userId, NotificationType notificationType) {
        this.id = id;
        this.message = message;
        this.checked = checked;
        this.userId = userId;
        this.notificationType = notificationType;
    }

    public Notification(Notification.Builder builder) {
        this.id = builder.id;
        this.message = builder.message;
        this.checked = builder.checked;
        this.userId = builder.userId;
        this.notificationType = builder.notificationType;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public boolean getChecked() {
        return checked;
    }

    public Long getUserId() {
        return userId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification notification = (Notification) o;
        return id == notification.id
            && message.equals(notification.message)
            && checked == notification.checked
            && userId == notification.userId
            && notificationType.equals(notification.notificationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, checked, userId, notificationType);
    }

    public static class Builder {

        private Long id;
        private String message;
        private boolean checked;
        private Long userId;
        private NotificationType notificationType;

        public Builder() {
        }

        public Notification.Builder id(long id) {
            this.id = id;
            return this;
        }

        public Notification.Builder message(String message) {
            this.message = message;
            return this;
        }

        public Notification.Builder checked(boolean checked) {
            this.checked = checked;
            return this;
        }

        public Notification.Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Notification.Builder notificationType(NotificationType notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }
}
