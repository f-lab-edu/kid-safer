package com.flab.kidsafer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.dto.PostRequestDTO;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostRequestControllerTest {

    private static int VALID_POSTID = 1;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext ctx;
    private User user;
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
        user = new User(1, "1234", "test@test", "test", "test", UserType.SAFER,
            Status.DEFAULT);
        loginUser = new SessionUser(user);
        session = new MockHttpSession();
        session.setAttribute("user", loginUser);
    }

    public String generatePostRequest() throws JsonProcessingException {
        PostRequestDTO postRequestDTO = new PostRequestDTO.Builder()
            .id(1)
            .postId(VALID_POSTID)
            .userId(loginUser.getId())
            .contents("????????? ????????? ???????????????.")
            .registerDate(LocalDateTime.now())
            .build();

        return objectMapper.writeValueAsString(postRequestDTO);
    }

    @Test
    @DisplayName("?????? ????????? ?????? ?????? ?????? ??????")
    public void getSingleRequest_success() throws Exception {
        mockMvc.perform(get("/posts/{postId}/requests/mine", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????? ??????")
    public void getSingleRequest_failure() throws Exception {
        session.setAttribute("user", null);
        mockMvc.perform(get("/posts/{postId}/requests/mine", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("?????? ???????????? ?????? ?????? ?????? ?????? ??????")
    public void getAllRequests_success() throws Exception {
        mockMvc.perform(get("/posts/{postId}/requests", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????? ??????")
    public void getAllRequests_failure() throws Exception {
        session.setAttribute("user", null);
        mockMvc.perform(get("/posts/{postId}/requests", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("????????? ?????? ??????")
    public void applyRequest_success() throws Exception {

        mockMvc.perform(post("/posts/{postId}/requests", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(generatePostRequest()))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("???????????? ????????? ????????????????????? ???????????? ?????? ??????")
    public void applyRequest_failure() throws Exception {
        /* ?????? ?????? ?????? */
        user.setType(UserType.PARENT);
        loginUser = new SessionUser(user);
        session.setAttribute("user", loginUser);

        mockMvc.perform(post("/posts/{postId}/requests", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(generatePostRequest()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("?????? ????????? ????????? ?????? ?????? ??????")
    public void applyRequest_failure2() throws Exception {
        mockMvc.perform(post("/posts/{postId}/requests", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(generatePostRequest()))
            .andExpect(status().isOk())
            .andDo(print());

        mockMvc.perform(post("/posts/{postId}/requests", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(generatePostRequest()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ??????")
    public void cancelRequest_success() throws Exception {

        mockMvc.perform(post("/posts/{postId}/requests", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(generatePostRequest()))
            .andExpect(status().isOk())
            .andDo(print());

        mockMvc.perform(delete("/posts/{postId}/requests", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(generatePostRequest()))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("????????? ????????? ????????? ?????? ?????? ??????")
    public void cancelRequest_failure() throws Exception {

        mockMvc.perform(delete("/posts/{postId}/requests", VALID_POSTID)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(generatePostRequest()))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }
}
