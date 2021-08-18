package com.flab.kidsafer.controller;

import com.flab.kidsafer.config.auth.LoginUser;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.dto.NotificationResponse;
import com.flab.kidsafer.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<NotificationResponse> getNotifications(@LoginUser SessionUser user) {
        NotificationResponse notificationResponse = notificationService.putCategorizedCheckedNotifications(user);
        return ResponseEntity.ok(notificationResponse);
    }

    @GetMapping("/old")
    public ResponseEntity<NotificationResponse> getOldNotifications(@LoginUser SessionUser user) {
        NotificationResponse notificationResponse = notificationService.putCategorizedUnCheckedNotifications(user);
        return ResponseEntity.ok(notificationResponse);
    }

    @DeleteMapping
    public void deleteNotifications(@LoginUser SessionUser user) {
        notificationService.deleteNotification(user);
    }
}
