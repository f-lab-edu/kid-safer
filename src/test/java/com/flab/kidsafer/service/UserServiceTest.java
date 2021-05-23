package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.mapper.UserMapper;
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
        SignInRequest loginRequest = new SignInRequest("cjk@gmail.com", "123");

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

        //when
        User user = userService.signIn(loginRequest);

        //then
        assertNull(user);
    }
}