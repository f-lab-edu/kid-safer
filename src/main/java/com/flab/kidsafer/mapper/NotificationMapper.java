package com.flab.kidsafer.mapper;

import com.flab.kidsafer.domain.Notification;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {

    Long countByUserAndChecked(int userId, boolean checked);

    List<Notification> selectNotificationByUserAndChecked(int userId, boolean checked);

    void insertNotification(Notification notifications);

    void updateNotificationsChecked(Notification notifications);

    void deleteNotification(int userId);
}
