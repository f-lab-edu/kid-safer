package com.flab.kidsafer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.dto.PostCommentDTO;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext ctx;

    private User user;

    private SessionUser loginUser;

    private MockHttpSession session;

    private PostCommentDTO 코멘트실패;
    private PostCommentDTO 첫번째코멘트;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
            .alwaysDo(print())
            .build();

        LocalDateTime currentTime = LocalDateTime.now();

        코멘트실패 = new PostCommentDTO.Builder()
            .commentLevel(1)
            .commentId(-1)
            .commentParentId(0)
            .commentWriterId(1)
            .role(null)
            .commentStatus("Y")
            .build();

        첫번째코멘트 = new PostCommentDTO.Builder()
            .commentLevel(1)
            .postId(1)
            .commentId(-1)
            .commentParentId(0)
            .commentContent("첫번째 댓글 입니다.")
            .commentWriterId(1)
            .role(null)
            .commentStatus("Y")
            .build();
    }

    @BeforeEach
    public void setSessionUser() {
        user = new User.Builder("1234", "test@test", UserType.ADMIN)
            .status(Status.DEFAULT)
            .nickname("test")
            .build();

        loginUser = new SessionUser(user);
        session = new MockHttpSession();
        session.setAttribute("user", loginUser);
    }

    @Test
    @DisplayName("코멘트 데이터 조회 성공")
    public void searchCommentSuccess() throws Exception {

        //when
        final ResultActions resultActions = requestSearchComment(1);

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("코멘트 데이터 등록 실패")
    public void createCommentFail() throws Exception {
        //when
        final ResultActions resultActions = requestRegisterComment(코멘트실패, session);

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    @DisplayName("코멘트 데이터 등록 성공")
    public void createCommentSuceess() throws Exception {
        //when
        final ResultActions resultActions = requestRegisterComment(첫번째코멘트, session);

        //then
        resultActions.andExpect(status().isOk());
    }

    @Transactional
    @Test
    @DisplayName("존재하지 않는 코멘트 데이터 업데이트 실패")
    public void updateCommentFail() throws Exception {

        //when
        final ResultActions resultActions = requestUpdateComment(첫번째코멘트, session);

        //then
        resultActions.andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    @DisplayName("존재하지 않는 코멘트 삭제 실패")
    public void deleteCommentFail() throws Exception {
        //when
        final ResultActions resultActions = requestDeleteComment(첫번째코멘트.getPostId(),
            첫번째코멘트.getCommentId(), session);

        //then
        resultActions.andExpect(status().isNotFound());
    }

    public ResultActions requestSearchComment(int postId) throws Exception {
        return mockMvc.perform(get("/posts/{postId}/comments", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());
    }

    public ResultActions requestRegisterComment(PostCommentDTO postCommentDTO,
        MockHttpSession seesionUser)
        throws Exception {

        return mockMvc.perform(post("/posts/{postId}/comments", postCommentDTO.getPostId())
            .contentType(MediaType.APPLICATION_JSON)
            .session(seesionUser)
            .content(objectMapper.writeValueAsString(postCommentDTO)))
            .andDo(print());
    }

    public ResultActions requestUpdateComment(PostCommentDTO postCommentDTO,
        MockHttpSession seesionUser)
        throws Exception {

        return mockMvc.perform(
            put("/posts/{postId}/comments/{commentId}", postCommentDTO.getPostId(),
                postCommentDTO.getCommentId())
                .contentType(MediaType.APPLICATION_JSON)
                .session(seesionUser)
                .content(objectMapper.writeValueAsString(postCommentDTO)))
            .andDo(print());
    }

    public ResultActions requestDeleteComment(int postId, int commentId,
        MockHttpSession seesionUser)
        throws Exception {

        return mockMvc.perform(delete("/posts/{postId}/comments/{commentId}", postId, commentId)
            .contentType(MediaType.APPLICATION_JSON)
            .session(seesionUser))
            .andDo(print());
    }
}
