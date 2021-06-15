package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.dto.UserUpdateInfoRequest;
import com.flab.kidsafer.dto.UserUpdatePasswordRequest;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.error.exception.PasswordInputInvalidException;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.mapper.UserMapper;
import com.flab.kidsafer.utils.SHA256Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {

    private static final String MEMBER_ID = "MEMBER_ID";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("DB에 등록된 대상자 로그인 성공")
    public void signInTest_success() {
        //given
        SignInRequest loginRequest = new SignInRequest("cjk@gmail.com", "1234!abc");

        //when
        User user = userService.signIn(loginRequest);

        //then
        assertEquals(user.getEmail(), "cjk@gmail.com");
    }

    @Test
    @DisplayName("회원 로그인 실패")
    public void signInTest_failure() {
        //given
        SignInRequest loginRequest = new SignInRequest("cjk@gmail.com", "12345");

        //then
        assertThrows(UserNotFoundException.class,
            () -> {
                userService.signIn(loginRequest);   //when
            });
    }

    @Test
    @DisplayName("세션 ID와 받아온 요청정보의 user ID가 달라 회원정보 수정 실패")
    public void modifyUserInfo_failure_operation_not_allowed() {
        //given
        UserUpdateInfoRequest userUpdateInfoRequest = new UserUpdateInfoRequest.Builder()
            .setUserId(1).setNickname("테스트").setPhone("010-1234-5678").build();
        int userId = 3;

        //then
        assertThrows(OperationNotAllowedException.class,
            () -> {
                userService.modifyUserInfo(userUpdateInfoRequest, userId);   //when
            });
    }

    @Test
    @DisplayName("사용자 정보 수정 성공")
    public void modifyUserInfo_success() {
        //given
        UserUpdateInfoRequest userUpdateInfoRequest = new UserUpdateInfoRequest.Builder()
            .setUserId(1).setNickname("테스트").setPhone("010-1234-5670").build();
        int userId = 1;

        //when
        userService.modifyUserInfo(userUpdateInfoRequest, userId);

        User user = userService.getUserById(userId);

        //then
        assertEquals(userUpdateInfoRequest.getNickname(), user.getNickname());
        assertEquals(userUpdateInfoRequest.getPhone(), user.getPhone());
    }

    @Test
    @DisplayName("기존 비밀번호 입력 불일치로 변경 실패")
    public void changePassword_failure_password_not_match() {
        //given
        UserUpdatePasswordRequest userUpdatePasswordRequest = new UserUpdatePasswordRequest.Builder()
            .setUserId(1).setCurrentPassword("xptmxm").setModifiedPassword("xptmxm").build();
        int userId = 1;

        //then
        assertThrows(PasswordInputInvalidException.class,
            () -> {
                userService.changePassword(userUpdatePasswordRequest, userId);   //when
            });
    }

    @Test
    @DisplayName("비밀번호 변경 성공")
    public void changePassword_success() {
        //given
        UserUpdatePasswordRequest userUpdatePasswordRequest = new UserUpdatePasswordRequest.Builder()
            .setUserId(1).setCurrentPassword("1234!abc").setModifiedPassword("12345").build();
        int userId = 1;

        //when
        userService.changePassword(userUpdatePasswordRequest, userId);

        //then
        User user = userService.getUserById(userId);
        assertEquals(SHA256Util.getSHA256(userUpdatePasswordRequest.getModifiedPassword()),
            user.getPassword());
    }
}
