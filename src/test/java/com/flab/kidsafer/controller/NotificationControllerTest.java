package com.flab.kidsafer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    private SessionUser loginUser;

    private MockHttpSession session;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    @BeforeEach
    public void setSessionUserAndPostId() {
        User user = new User(1, "1234", "test@test", "test", "test", UserType.PARENT,
            Status.DEFAULT);
        loginUser = new SessionUser(user);
        session = new MockHttpSession();
        session.setAttribute("user", loginUser);
    }

    @Test
    @DisplayName("읽지 않은 알림 조회 성공")
    public void not_read_notifications_search() throws Exception {
        mockMvc.perform(get("/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("읽은 알림 조회 성공")
    public void read_notification_search() throws Exception {
        mockMvc.perform(get("/notifications/old")
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("알림 삭제 성공")
    public void delete_notification() throws Exception {
        mockMvc.perform(delete("/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());
    }
}
