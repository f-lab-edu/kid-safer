package com.flab.kidsafer.event;

import com.flab.kidsafer.domain.Notification;
import com.flab.kidsafer.domain.enums.NotificationType;
import com.flab.kidsafer.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Async
@Component
public class UserStatusListener {

    @Autowired
    private NotificationMapper notificationMapper;

    @EventListener
    public void handleUserUpdateBlock(UserStatusEvent userStatusEvent) {
        createNotification(userStatusEvent.getUserId(), userStatusEvent.getMessage(),
            NotificationType.BLOCK_STATUE_CHANGE);
    }

    private void createNotification(int userId, String message, NotificationType notificationType) {
        Notification notification = new Notification.Builder()
            .checked(false)
            .message(message)
            .userId(userId)
            .notificationType(notificationType)
            .build();

        notificationMapper.insertNotification(notification);
    }
}
