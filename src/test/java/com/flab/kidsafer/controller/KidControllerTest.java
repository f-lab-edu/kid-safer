package com.flab.kidsafer.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.kidsafer.domain.Kid;
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
@Transactional
class KidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
            .alwaysDo(print())
            .build();
    }

    @Test
    @DisplayName("성별 입력 값 F,M 아닐 경우 오류 발생")
    public void registerKid_fail_genderInvalid() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        //given
        final Kid kids = new Kid(1, 1, "S", "2005", null, currentTime, currentTime);

        //when
        final ResultActions resultActions = requestRegisterKid(kids);

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("출생년도 문자 입력시 오류 발생")
    public void registerKid_fail_birthYearInvalid() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        //given
        final Kid kids = new Kid(1, 1, "F", "abcd", null, currentTime, currentTime);

        //when
        final ResultActions resultActions = requestRegisterKid(kids);

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("미성년이 아닐시 오류 발생")
    public void registerKid_fail_birthYearInvalid2() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        //given
        final Kid kids = new Kid(1, 1, "F", "1990", null, currentTime, currentTime);

        //when
        final ResultActions resultActions = requestUpdateKid(kids);

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("미성년자이며 성별 데이터 입력 값 유효시 등록 성공")
    public void registerKid_suceess_AllDataValid() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        //given
        final Kid kids = new Kid(1, 1, "F", "2020", null, currentTime, currentTime);

        //when
        final ResultActions resultActions = requestRegisterKid(kids);

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("미성년이 아닐시 오류 발생")
    public void updateKid_fail_birthYearInvalid() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        //given
        final Kid kids = new Kid(33, 1, "F", "1990", null, currentTime, currentTime);

        //when
        final ResultActions resultActions = requestUpdateKid(kids);

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("아이 데이터 수정 성공")
    public void updateKid_success() throws Exception {
        LocalDateTime currentTime = LocalDateTime.now();
        //given
        final Kid kids = new Kid(33, 1, "F", "2021", null, currentTime, currentTime);

        //when
        final ResultActions resultActions = requestUpdateKid(kids);

        //then
        resultActions.andExpect(status().isOk());
    }

    public ResultActions requestRegisterKid(Kid kid) throws Exception {
        return mockMvc.perform(post("/kids").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(kid))).andDo(print());
    }

    public ResultActions requestUpdateKid(Kid kid) throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("MEMBER_ID", kid.getParentId());

        return mockMvc.perform(put("/kids/" + kid.getId()).contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(objectMapper.writeValueAsString(kid))).andDo(print());
    }
}
