package com.flab.kidsafer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.dto.PostDTO;
import java.time.LocalDate;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class PostControllerTest {

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
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
            .alwaysDo(print())
            .build();
    }

    @BeforeEach
    public void setSessionUser() {
        user = new User(1, "1234", "test@test", "test", "test", UserType.PARENT, Status.DEFAULT);
        loginUser = new SessionUser(user);
        session = new MockHttpSession();
        session.setAttribute("user", loginUser);
    }

    @Test
    @DisplayName("post 조회 성공")
    public void getOnePost_success() throws Exception {
        mockMvc.perform(get("/posts/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("DB에 존재하지 않는 postid로 조회하여 실패")
    public void getOnePost_failure() throws Exception {
        mockMvc.perform(get("/posts/9999")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(print());

    }

    @Test
    @DisplayName("post 등록 성공")
    public void registerPost_success() throws Exception {
        PostDTO post = new PostDTO.Builder(1).setParentId(1)
            .setDistrictId(100)
            .setTitle("도우미를 구합니다.")
            .setContents("주 3회 등하원 도우미를 구합니다.")
            .setFee(30000)
            .setStartDate(LocalDate.of(2021, 5, 1))
            .setEndDate(LocalDate.of(2021, 12, 31))
            .setRegisterDate(LocalDateTime.now())
            .setDueDate(LocalDateTime.parse("2021-04-30T10:00:00"))
            .build();

        /* 객체를 JSON으로 변환하여 요청으로 전달 */
        String postJsonString = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(postJsonString))
            .andExpect(status().isOk())
            .andDo(print());

    }

    @Test
    @DisplayName("필수 입력 프로퍼티가 null로 인해 post 등록 실패")
    public void registerPost_failure() throws Exception {
        PostDTO post = new PostDTO.Builder(1).setParentId(1)
            .setDistrictId(100)
            .setTitle(null)                 // title null값 입력
            .setContents("주 3회 등하원 도우미를 구합니다.")
            .build();

        /* 객체를 JSON으로 변환하여 요청으로 전달 */
        String postJsonString = objectMapper.writeValueAsString(post);

        mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(postJsonString))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    @DisplayName("post 정보 수정 성공")
    public void modifyPostInfo_success() throws Exception {
        PostDTO post = new PostDTO.Builder(1).setParentId(1)
            .setDistrictId(100)
            .setTitle("도우미를 구합니다.(수정)")         // 값 수정
            .setContents("주 3회 등하원 도우미를 구합니다.")
            .setFee(30000)
            .setStartDate(LocalDate.of(2021, 5, 1))
            .setEndDate(LocalDate.of(2021, 12, 31))
            .setDueDate(LocalDateTime.parse("2021-04-30T10:00:00"))
            .build();

        /* 객체를 JSON으로 변환하여 요청으로 전달 */
        String postJsonString = objectMapper.writeValueAsString(post);

        session.setAttribute("MEMBER_ID", post.getParentId());

        mockMvc.perform(put("/posts/1")
            .characterEncoding("uft-8")
            .content(postJsonString)
            .session(session)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("수정 요청자와 작성자가 달라 post 정보 수정 실패")
    public void modifyPostInfo_failure() throws Exception {
        PostDTO post = new PostDTO.Builder(1).setParentId(1)
            .setDistrictId(100)
            .setTitle("도우미를 구합니다.(수정)")         // 값 수정
            .build();

        /* 객체를 JSON으로 변환하여 요청으로 전달 */
        String postJsonString = objectMapper.writeValueAsString(post);

        /* 세션 정보 변경 */
        user.setUserId(post.getParentId() + 1);
        loginUser = new SessionUser(user);
        session.setAttribute("user", loginUser);

        mockMvc.perform(put("/posts/1")
            .characterEncoding("uft-8")
            .content(postJsonString)
            .session(session)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("post 삭제 성공")
    public void deletePostInfo_success() throws Exception {

        mockMvc.perform(delete("/posts/1")
            .characterEncoding("uft-8")
            .session(session)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("삭제 요청자와 작성자가 달라 post 삭제 실패")
    public void deletePostInfo_failure() throws Exception {

        /* 세션 정보 변경 */
        user.setUserId(9999);
        loginUser = new SessionUser(user);
        session.setAttribute("user", loginUser);

        mockMvc.perform(delete("/posts/1")
            .characterEncoding("uft-8")
            .session(session)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}
