package com.flab.kidsafer.service;

import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.Notification;
import com.flab.kidsafer.domain.enums.NotificationType;
import com.flab.kidsafer.dto.NotificationResponse;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.mapper.NotificationMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public NotificationResponse putCategorizedCheckedNotifications(SessionUser user) {
        if (user == null) {
            throw new OperationNotAllowedException();
        }

        List<Notification> notifications = notificationMapper.selectNotificationByUserAndChecked(user.getId(), true);
        long numberOfChecked = notificationMapper.countByUserAndChecked(user.getId(), true);

        Map<NotificationType, List<Notification>> requestNotifications = notifications.stream()
            .collect(Collectors.groupingBy(Notification::getNotificationType));

        NotificationResponse notificationResponse = new NotificationResponse.Builder()
            .isChecked(true)
            .numberOfNotChecked(notifications.size())
            .numberOfNotChecked(numberOfChecked)
            .notifications(notifications)
            .requestNotifications(requestNotifications)
            .build();

        return notificationResponse;
    }

    public NotificationResponse putCategorizedUnCheckedNotifications(SessionUser user) {
        if (user == null) {
            throw new OperationNotAllowedException();
        }

        List<Notification> notifications = notificationMapper
            .selectNotificationByUserAndChecked(user.getId(), false);
        long numberOfChecked = notificationMapper.countByUserAndChecked(user.getId(), false);

        Map<NotificationType, List<Notification>> requestNotifications = notifications.stream()
            .collect(Collectors.groupingBy(Notification::getNotificationType));

        NotificationResponse notificationResponse = new NotificationResponse.Builder()
            .isChecked(false)
            .numberOfNotChecked(notifications.size())
            .numberOfNotChecked(numberOfChecked)
            .notifications(notifications)
            .requestNotifications(requestNotifications)
            .build();

        markAsRead(notifications);

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
