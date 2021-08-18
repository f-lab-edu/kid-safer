package com.flab.kidsafer.service;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.Notification;
import com.flab.kidsafer.domain.PostLike;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.NotificationType;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.dto.NotificationResponse;
import com.flab.kidsafer.dto.PostDTO;
import com.flab.kidsafer.error.exception.PostNotFoundException;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.mapper.NotificationMapper;
import com.flab.kidsafer.mapper.PostLikeMapper;
import com.flab.kidsafer.mapper.PostMapper;
import com.flab.kidsafer.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationMapper notificationMapper;

    private User parent;
    private User safer;

    private Notification notificationBlock;
    private Notification notificationApplication;
    private Notification notificationRequestSuccess;
    private Notification notificationRequestFail;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void generateNotification() {
        parent = new User.Builder("test", "test", UserType.PARENT).build();
        safer = new User.Builder("test2", "test2", UserType.SAFER).build();

        notificationBlock = new Notification.Builder().id(1).message("계정이 차단되었습니다.").userId(1).checked(false).notificationType(
            NotificationType.BLOCK_STATUE_CHANGE).build();
        notificationApplication = new Notification.Builder().id(1).message("등하원 요청에 대한 신청이 들어왔습니다.").userId(1).checked(false).notificationType(
            NotificationType.REQUEST_APPLICATION).build();
        notificationRequestSuccess = new Notification.Builder().id(2).message("등하원 신청이 수락 되었습니다.").userId(1).checked(true).notificationType(
            NotificationType.REQUEST_SUCCESS).build();
        notificationRequestFail = new Notification.Builder().id(2).message("등하원 신청이 거절 되었습니다.").userId(1).checked(false).notificationType(
            NotificationType.REQUEST_FAIL).build();
    }

    public List<Notification> generateNotReadNotification(boolean isChecked) {
        List<Notification> notifications = new ArrayList<>();

        notifications.add(notificationBlock);
        notifications.add(notificationApplication);
        notifications.add(notificationRequestSuccess);
        notifications.add(notificationRequestFail);

        return notifications.stream()
            .filter(notification -> notification.getChecked() == isChecked)
            .collect(toList());
    }

    @Test
    @DisplayName("읽지 않은 알림 검색")
    public void search_not_read() {
        // given
        doNothing().when(notificationMapper).insertNotification(notificationBlock);
        doNothing().when(notificationMapper).insertNotification(notificationApplication);
        doNothing().when(notificationMapper).insertNotification(notificationRequestSuccess);
        doNothing().when(notificationMapper).insertNotification(notificationRequestFail);
        when(notificationMapper.selectNotificationByUserAndChecked(parent.getUserId(), false)).thenReturn(generateNotReadNotification(false));
        when(notificationMapper.countByUserAndChecked(parent.getUserId(), false)).thenReturn(3L);

        // when
        notificationService.putCategorizedUnCheckedNotifications(new SessionUser(parent));

        // then
        verify(notificationMapper, times(3)).updateNotificationsChecked(any(Notification.class));
    }

    @Test
    @DisplayName("읽은 알림 검색")
    public void search_read() {
        // given
        doNothing().when(notificationMapper).insertNotification(notificationBlock);
        doNothing().when(notificationMapper).insertNotification(notificationApplication);
        doNothing().when(notificationMapper).insertNotification(notificationRequestSuccess);
        doNothing().when(notificationMapper).insertNotification(notificationRequestFail);
        when(notificationMapper.selectNotificationByUserAndChecked(parent.getUserId(), true)).thenReturn(generateNotReadNotification(false));
        when(notificationMapper.countByUserAndChecked(parent.getUserId(), true)).thenReturn(3L);

        // when
        NotificationResponse notificationResponse = notificationService.putCategorizedCheckedNotifications(new SessionUser(parent));

        // then
        verify(notificationMapper, never()).updateNotificationsChecked(any(Notification.class));
    }

    @Test
    @DisplayName("알림 삭제")
    public void delete_notification() {
        // given
        doNothing().when(notificationMapper).insertNotification(notificationBlock);
        doNothing().when(notificationMapper).insertNotification(notificationApplication);
        doNothing().when(notificationMapper).insertNotification(notificationRequestSuccess);
        doNothing().when(notificationMapper).insertNotification(notificationRequestFail);

        // when
        notificationService.deleteNotification(new SessionUser(parent));

        // then
        verify(notificationMapper).deleteNotification(any(Integer.class));
    }
}
