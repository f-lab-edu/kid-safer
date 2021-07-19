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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostLikeControllerTest {

    private static int INVALID_POSTID = -999;
    private static int VALID_POSTID = 1;
    private static int ALREADY_LIKE_POSTID = 1;
    private static int DELETED_LIKE_POSTID = 3;
    private static int TOBE_DELETED_LIKE_POSTID = 1;

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
        User user = new User(1, "1234", "test@test", "test", "test", UserType.PARENT, Status.DEFAULT);
        loginUser = new SessionUser(user);
        session = new MockHttpSession();
        session.setAttribute("user", loginUser);
    }

    @Test
    @DisplayName("좋아요 성공")
    public void like_success() throws Exception {
        mockMvc.perform(post("/posts/{postId}/likes", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("이미 좋아요를 한 post에 다시 좋아요 시도해 실패")
    public void like_failure1() throws Exception {
        mockMvc.perform(post("/posts/{postId}/likes", ALREADY_LIKE_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());
        mockMvc.perform(post("/posts/{postId}/likes", ALREADY_LIKE_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isBadRequest())
            .andDo(print());

    }

    @Test
    @DisplayName("해당되는 포스트가 없어 좋아요 실패")
    public void like_failure2() throws Exception {
        mockMvc.perform(post("/posts/{postId}/likes", INVALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isNotFound())
            .andDo(print());

    }

    @Test
    @DisplayName("좋아요 취소 성공")
    public void unlike_success() throws Exception {
        /* 좋아요 먼저 등록 */
        mockMvc.perform(post("/posts/{postId}/likes", TOBE_DELETED_LIKE_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());

        mockMvc.perform(delete("/posts/{postId}/likes", TOBE_DELETED_LIKE_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("좋아요한 내역이 없어 좋아요 취소 실패")
    public void unlike_failure1() throws Exception {
        mockMvc.perform(delete("/posts/{postId}/likes", DELETED_LIKE_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isNotFound())
            .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 포스트이므로 좋아요 취소 실패")
    public void unlike_failure2() throws Exception {
        mockMvc.perform(delete("/posts/{postId}/likes", INVALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isNotFound())
            .andDo(print());

    }
}
