package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.event.UserStatusEvent;
import com.flab.kidsafer.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void blockUser(int userId) {
        if (userMapper.findById(userId) == null) {
            throw new UserNotFoundException();
        }

        eventPublisher.publishEvent(
            new UserStatusEvent.Builder()
                .userId(userId)
                .message("계정이 차단 되었습니다.")
                .build()
        );

        userMapper.updateUserStatus(userId, Status.BLOCKED);
    }

    public void unblockUser(int userId) {
        if (userMapper.findById(userId) == null) {
            throw new UserNotFoundException();
        }

        eventPublisher.publishEvent(
            new UserStatusEvent.Builder()
                .userId(userId)
                .message("계정의 차단이 해제 되었습니다.")
                .build()
        );

        userMapper.updateUserStatus(userId, Status.DEFAULT);
    }
}
