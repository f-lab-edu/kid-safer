package com.flab.kidsafer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.kidsafer.domain.SignInRequest;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc /* 테스트에 사용할 가상의 WAS */
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("/users/signIn 접근 가능한지 확인")
    public void testSignIn() throws Exception {

        /* 입력 parameter 주입 */
        MultiValueMap<String, String> loginRequest = new LinkedMultiValueMap<>();
        loginRequest.add("email", "");
        loginRequest.add("password", "");

        mockMvc.perform(post("/users/signIn")       // 요청을 전송하는 역할. 결과로 ResultActions 객체를 받음,
            .params(loginRequest))                  // 키=값의 파라미터 전달(여러개일때는 params(), 한개일때는 param() 사용)
            .andExpect(status().is(400))            // 응답을 검증하는 역할
            .andDo(print());                        // 응답/요청 전체 메시지 확인
    }

    @Test
    @DisplayName("이메일 주소가 규격에 맞지않거나 null일 경우 오류를 반환한다.")
    public void testSignInResponseValidation() throws Exception {
        /* 이메일 형식이 아닐 경우 */
        SignInRequest.Builder builder = new SignInRequest.Builder("test1.com", "1234");
        SignInRequest signInRequest = builder.build();

        /* 객체를 JSON으로 변환하여 요청으로 전달 */
        String loginRequestJsonString = objectMapper.writeValueAsString(signInRequest);

        mockMvc.perform(post("/users/signIn")
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginRequestJsonString))
            .andExpect(status().isBadRequest())
            .andDo(print());

        /* password가 빈 값일 경우 */
        builder = new SignInRequest.Builder("123@gmail.com", "");
        signInRequest = builder.build();

        loginRequestJsonString = objectMapper.writeValueAsString(signInRequest);

        mockMvc.perform(post("/users/signIn")
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginRequestJsonString))
            .andExpect(rslt -> assertTrue(
                Objects.requireNonNull(rslt.getResolvedException()).getClass()
                    .isAssignableFrom(MethodArgumentNotValidException.class)))
            .andDo(print());
    }
}