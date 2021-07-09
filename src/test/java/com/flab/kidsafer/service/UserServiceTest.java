package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.dto.UserUpdateInfoRequest;
import com.flab.kidsafer.dto.UserUpdatePasswordRequest;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.error.exception.PasswordInputInvalidException;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.mapper.UserMapper;
import com.flab.kidsafer.utils.SHA256Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    private static final String MEMBER_ID = "MEMBER_ID";

    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    private UserUpdatePasswordRequest userUpdatePasswordRequest;

    @BeforeEach
    public void initData() {
        userUpdatePasswordRequest = new UserUpdatePasswordRequest.Builder()
            .setUserId(1).setCurrentPassword("1234!abc").setModifiedPassword("12345").build();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("DB에 등록된 대상자 로그인 성공")
    public void signInTest_success() {
        //given
        SignInRequest loginRequest = new SignInRequest("cjk@gmail.com", "1234!abc");
        when(userMapper.findByEmailAndPassword(loginRequest.getEmail(),
            SHA256Util.getSHA256(loginRequest.getPassword())))
            .thenReturn(new User.Builder("cjk@gmail.com", "1234!abc", UserType.PARENT).build());

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
        when(userMapper.findById(userUpdateInfoRequest.getUserId()))
            .thenReturn(new User.Builder("cjk@gmail.com", "1234!abc", UserType.PARENT).build());

        //when
        userService.modifyUserInfo(userUpdateInfoRequest, userId);

        //then
        verify(userMapper).updateUserInfo(any(UserUpdateInfoRequest.class));
    }

    @Test
    @DisplayName("기존 비밀번호 입력 불일치로 변경 실패")
    public void changePassword_failure_password_not_match() {
        //given
        UserUpdatePasswordRequest userUpdatePasswordRequest = new UserUpdatePasswordRequest.Builder()
            .setUserId(1).setCurrentPassword("xptmxm").setModifiedPassword("xptmxm").build();
        int userId = 1;
        when(userMapper.findById(userUpdatePasswordRequest.getUserId()))
            .thenReturn(new User.Builder("cjk@gmail.com", "1234!abc", UserType.PARENT).build());

        //then
        assertThrows(PasswordInputInvalidException.class,
            () -> {
                userService.changePassword(userUpdatePasswordRequest, userId);   //when
            });
        verify(userMapper, times(0)).updateUserPassword(userUpdatePasswordRequest.getUserId(),
            userUpdatePasswordRequest.getModifiedPassword());
    }

    @Test
    @DisplayName("비밀번호 변경 성공")
    public void changePassword_success() {
        //given
        String encryptPassword = SHA256Util
            .getSHA256(userUpdatePasswordRequest.getCurrentPassword());
        String encryptModiPassword = SHA256Util
            .getSHA256(userUpdatePasswordRequest.getModifiedPassword());

        when(userMapper.findById(userUpdatePasswordRequest.getUserId()))
            .thenReturn((new User.Builder("cjk@gmail.com",
                encryptPassword, UserType.PARENT)
                .build()));
        doNothing().when(userMapper)
            .updateUserPassword(userUpdatePasswordRequest.getUserId(), encryptModiPassword);

        //when
        userService
            .changePassword(userUpdatePasswordRequest, userUpdatePasswordRequest.getUserId());

        // then
        verify(userMapper)
            .updateUserPassword(userUpdatePasswordRequest.getUserId(), encryptModiPassword);
    }
}
