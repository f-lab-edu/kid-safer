package com.flab.kidsafer.dto;

import com.flab.kidsafer.domain.Notification;
import java.util.List;

public class NotificationResponse {

    boolean isChecked;
    long numberOfNotChecked;
    long numberOfChecked;
    List<Notification> notifications;
    List<Notification> requestApplicationNotifications;
    List<Notification> requestSuccessNotifications;
    List<Notification> blockStatusNotifications;

    public NotificationResponse(NotificationResponse.Builder builder) {
        this.isChecked = builder.isChecked;
        this.numberOfNotChecked = builder.numberOfNotChecked;
        this.numberOfChecked = builder.numberOfChecked;
        this.notifications = builder.notifications;
        this.requestApplicationNotifications = builder.requestApplicationNotifications;
        this.requestSuccessNotifications = builder.requestSuccessNotifications;
        this.blockStatusNotifications = builder.blockStatusNotifications;
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

    public List<Notification> getRequestApplicationNotifications() {
        return requestApplicationNotifications;
    }

    public List<Notification> getRequestSuccessNotifications() {
        return requestSuccessNotifications;
    }

    public List<Notification> getBlockStatusNotifications() {
        return blockStatusNotifications;
    }

    public static class Builder {

        boolean isChecked;
        long numberOfNotChecked;
        long numberOfChecked;
        List<Notification> notifications;
        List<Notification> requestApplicationNotifications;
        List<Notification> requestSuccessNotifications;
        List<Notification> blockStatusNotifications;

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

        public NotificationResponse.Builder requestApplicationNotifications(
            List<Notification> requestApplicationNotifications) {
            this.requestApplicationNotifications = requestApplicationNotifications;
            return this;
        }

        public NotificationResponse.Builder requestSuccessNotifications(
            List<Notification> requestSuccessNotifications) {
            this.requestSuccessNotifications = requestSuccessNotifications;
            return this;
        }

        public NotificationResponse.Builder blockStatusNotifications(
            List<Notification> blockStatusNotifications) {
            this.blockStatusNotifications = blockStatusNotifications;
            return this;
        }


        public NotificationResponse build() {
            return new NotificationResponse(this);
        }
    }
}
