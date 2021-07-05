package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserMapper userMapper;

    public void blockUser(int userId) {
        if (userMapper.findById(userId) == null) {
            throw new UserNotFoundException();
        }
        userMapper.updateUserStatus(userId, Status.BLOCKED);
    }

    public void unblockUser(int userId) {
        if (userMapper.findById(userId) == null) {
            throw new UserNotFoundException();
        }
        userMapper.updateUserStatus(userId, Status.DEFAULT);
    }
}
