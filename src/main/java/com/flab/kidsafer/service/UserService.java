package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.LoginRequest;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.mapper.UserMapper;
import com.flab.kidsafer.utils.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User signUp(LoginRequest loginRequest) {
        String id = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        // 입력받은 비밀번호는 암호화한다.
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        return userMapper.findByIdAndPassword(id, cryptoPassword);
    }
}
