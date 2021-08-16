package com.flab.kidsafer.dto;

import com.flab.kidsafer.domain.Notification;
import com.flab.kidsafer.domain.enums.NotificationType;
import java.util.List;
import java.util.Map;

public class NotificationResponse {

    private boolean isChecked;
    private long numberOfNotChecked;
    private long numberOfChecked;
    private List<Notification> notifications;
    private Map<NotificationType, List<Notification>> requestNotifications;

    public NotificationResponse(NotificationResponse.Builder builder) {
        this.isChecked = builder.isChecked;
        this.numberOfNotChecked = builder.numberOfNotChecked;
        this.numberOfChecked = builder.numberOfChecked;
        this.notifications = builder.notifications;
        this.requestNotifications = builder.requestNotifications;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public long getNumberOfNotChecked() {
        return numberOfNotChecked;
    }

    public long getNumberOfChecked() {
        return numberOfChecked;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Map<NotificationType, List<Notification>> getRequestNotifications() {
        return requestNotifications;
    }

    public static class Builder {

        boolean isChecked;
        long numberOfNotChecked;
        long numberOfChecked;
        List<Notification> notifications;
        private Map<NotificationType, List<Notification>> requestNotifications;

        public Builder() {
        }

        public NotificationResponse.Builder isChecked(boolean isChecked) {
            this.isChecked = isChecked;
            return this;
        }

        public NotificationResponse.Builder numberOfNotChecked(long numberOfNotChecked) {
            this.numberOfNotChecked = numberOfNotChecked;
            return this;
        }

        public NotificationResponse.Builder numberOfChecked(long numberOfChecked) {
            this.numberOfChecked = numberOfChecked;
            return this;
        }

        public NotificationResponse.Builder notifications(List<Notification> notifications) {
            this.notifications = notifications;
            return this;
        }

        public NotificationResponse.Builder requestNotifications(Map<NotificationType, List<Notification>> requestApplicationNotifications) {
            this.requestNotifications = requestNotifications;
            return this;
        }

        public NotificationResponse build() {
            return new NotificationResponse(this);
        }
    }
}
