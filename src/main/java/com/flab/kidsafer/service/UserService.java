package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.UserDto;
import com.flab.kidsafer.error.exception.DuplicateIdException;
import com.flab.kidsafer.dto.UserUpdateInfoRequest;
import com.flab.kidsafer.dto.UserUpdatePasswordRequest;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.error.exception.PasswordInputInvalidException;
import com.flab.kidsafer.error.exception.UserNotAuthorizedException;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.mapper.UserMapper;
import com.flab.kidsafer.utils.SHA256Util;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public User getUserByEmailAndPwd(String email, String cryptoPassword) {
        return userMapper.findByEmailAndPassword(email, cryptoPassword);
    }

    public User getUserByIdAndPwd(int userId, String cryptoPassword) {
        return userMapper.findByIdAndPassword(userId, cryptoPassword);
    }

    public User getUserById(int userId) {
        return userMapper.findById(userId);
    }

    public User signIn(SignInRequest signInRequest) {
        LOGGER.info("signIn started");
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        String cryptoPassword = SHA256Util.getSHA256(password);

        User user = getUserByEmailAndPwd(email, cryptoPassword);

        if (user == null) {
            throw new UserNotFoundException();
        } else if ("NOT_AUTHORIZED".equals(user.getStatus())) {  // 이메일 인증을 받지 못한 사용자
            throw new UserNotAuthorizedException();
        }
        return user;
    }

    public void signUp(UserDto userDto) {
        saveNewUser(userDto);

        //TODO: 메일 보내기 추가 예정

    }

    private void saveNewUser(@Valid UserDto userDto) {
        User duplicationUser = userMapper.findByEmail(userDto.getEmail());

        if (duplicationUser != null) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }

        userDto.setPassword(SHA256Util.getSHA256(userDto.getPassword()));
        User newUser = new User.Builder(userDto.getEmail(), userDto.getPassword(),
            userDto.getType())
            .nickname(userDto.getNickname())
            .phone(userDto.getPhone())
            .status(userDto.getStatus())
            .build();

        int insertCount = userMapper.insertUser(newUser);

        if (insertCount != 1) {
            throw new RuntimeException("회원가입 입력값을 확인해 주세요" + newUser);
        }
    }

    public void modifyUserInfo(UserUpdateInfoRequest userUpdateInfoRequest, int userId) {
        isSameUser(userUpdateInfoRequest.getUserId(), userId);
        userMapper.updateUserInfo(userUpdateInfoRequest);
    }

    public void changePassword(UserUpdatePasswordRequest userUpdatePasswordRequest,
        int userId) {
        isSameUser(userUpdatePasswordRequest.getUserId(), userId);

        User user = getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }

        String cryptCurPwd = SHA256Util.getSHA256(userUpdatePasswordRequest.getCurrentPassword());
        if (!user.getPassword().equals(cryptCurPwd)) {
            throw new PasswordInputInvalidException();
        }

        String cryptPwd = SHA256Util.getSHA256(userUpdatePasswordRequest.getModifiedPassword());
        userMapper.updateUserPassword(userId, cryptPwd);
    }

    public void isSameUser(int requestUserId, int sessionUserId) {
        if (requestUserId != sessionUserId) {
            throw new OperationNotAllowedException();
        }
    }
}
