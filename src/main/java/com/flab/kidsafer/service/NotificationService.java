package com.flab.kidsafer.service;

import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.Notification;
import com.flab.kidsafer.dto.NotificationResponse;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.mapper.NotificationMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public NotificationResponse putCategorizedNotifications(SessionUser user, boolean isChecked) {
        if (user == null) {
            throw new OperationNotAllowedException();
        }

        List<Notification> notifications = notificationMapper
            .selectNotificationByUserAndChecked(user.getId(), isChecked);
        long numberOfChecked = notificationMapper.countByUserAndChecked(user.getId(), isChecked);

        List<Notification> requestApplicationNotifications = new ArrayList<>();
        List<Notification> requestSuccessNotifications = new ArrayList<>();
        List<Notification> blockStatusNotifications = new ArrayList<>();
        for (var notification : notifications) {
            switch (notification.getNotificationType()) {
                case "REQUEST_APPLICATION":
                    requestApplicationNotifications.add(notification);
                    break;
                case "REQUEST_SUCCESS":
                    requestSuccessNotifications.add(notification);
                    break;
                case "BLOCK_STATUE_CHANGE":
                    blockStatusNotifications.add(notification);
                    break;
            }
        }

        NotificationResponse notificationResponse = new NotificationResponse.Builder()
            .isChecked(isChecked)
            .numberOfNotChecked(notifications.size())
            .numberOfNotChecked(numberOfChecked)
            .notifications(notifications)
            .requestApplicationNotifications(requestApplicationNotifications)
            .requestSuccessNotifications(requestSuccessNotifications)
            .blockStatusNotifications(blockStatusNotifications)
            .build();

        if (!isChecked) {
            markAsRead(notifications);
        }

        return notificationResponse;
    }

    private void markAsRead(List<Notification> notifications) {
        notifications.forEach(n -> notificationMapper.updateNotificationsChecked(n));
    }

    public void deleteNotification(SessionUser user) {
        if (user == null) {
            throw new OperationNotAllowedException();
        }

        notificationMapper.deleteNotification(user.getId());
    }
}
