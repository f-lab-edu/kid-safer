package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.mapper.UserMapper;
import com.flab.kidsafer.utils.SHA256Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public User signIn(SignInRequest signInRequest) {
        LOGGER.info("signIn started");
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        // 입력받은 비밀번호는 암호화한다.
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        return userMapper.findByEmailAndPassword(email, cryptoPassword);
    }
}
