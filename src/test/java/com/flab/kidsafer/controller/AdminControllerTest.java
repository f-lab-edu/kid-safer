package com.flab.kidsafer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class AdminControllerTest {

    private final static int DEFAULT_USERID = 1;
    private final static int INVALID_USERID = -1;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;

    private SessionUser loginUser;
    private MockHttpSession session;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
            .alwaysDo(print())
            .build();
    }

    @BeforeEach
    public void setSessionUser() {
        User user = new User(1, "1234", "test@test", "test", "test", UserType.ADMIN, Status.DEFAULT);
        loginUser = new SessionUser(user);
        session = new MockHttpSession();
        session.setAttribute("user", loginUser);
    }

    public void setSessionUserNotAdmin() {
        User user = new User(1, "1234", "test@test", "test", "test", UserType.PARENT, Status.DEFAULT);
        loginUser = new SessionUser(user);
        session = new MockHttpSession();
        session.setAttribute("user", loginUser);
    }

    @Test
    @DisplayName("사용자 차단 성공")
    public void blockUser_success() throws Exception {

        mockMvc.perform(post("/users/{userId}/block", DEFAULT_USERID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("차단 시도하는 대상자가 관리자가 아니기에 실패")
    public void blockUser_failure() throws Exception {
        setSessionUserNotAdmin();
        mockMvc.perform(post("/users/{userId}/block", DEFAULT_USERID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isBadRequest())
            .andDo(print());

    }

    @Test
    @DisplayName("해당하는 대상자가 없어 실패")
    public void blockUser_failure2() throws Exception {
        mockMvc.perform(post("/users/{userId}/block", INVALID_USERID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isNotFound())
            .andDo(print());

    }

    @Test
    @DisplayName("차단 취소 성공")
    public void unblock_success() throws Exception {
        mockMvc.perform(delete("/users/{userId}/block", DEFAULT_USERID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("차단해제 시도하는 대상자가 관리자가 아니기에 실패")
    public void unblock_failure1() throws Exception {
        setSessionUserNotAdmin();
        mockMvc.perform(delete("/users/{userId}/block", DEFAULT_USERID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isBadRequest())
            .andDo(print());

    }

    @Test
    @DisplayName("해당하는 대상자가 없어 실패")
    public void unblock_failure2() throws Exception {
        mockMvc.perform(delete("/users/{userId}/block", INVALID_USERID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isNotFound())
            .andDo(print());

    }
}
