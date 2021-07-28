package com.flab.kidsafer.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.error.exception.TokenInvalidException;
import com.flab.kidsafer.error.exception.UserNotSignInException;
import com.flab.kidsafer.mapper.UserMapper;
import com.flab.kidsafer.utils.SHA256Util;
import java.util.Objects;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureMockMvc /* 테스트에 사용할 가상의 WAS */
@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
            .alwaysDo(print())
            .build();
    }

    @Test
    @DisplayName("/users/signIn 접근 가능한지 확인")
    public void testSignIn() throws Exception {

        /* 입력 parameter 주입 */
        MultiValueMap<String, String> loginRequest = new LinkedMultiValueMap<>();
        loginRequest.add("email", "");
        loginRequest.add("password", "");

        mockMvc.perform(post("/users/signIn")       // 요청을 전송하는 역할. 결과로 ResultActions 객체를 받음,
            .params(
                loginRequest))                  // 키=값의 파라미터 전달(여러개일때는 params(), 한개일때는 param() 사용)
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

    @Test
    @DisplayName("로그아웃 성공")
    public void testSignOut_success() throws Exception {

        MockHttpSession session = new MockHttpSession();

        session.setAttribute("MEMBER_ID", 1);

        mockMvc.perform(post("/users/signOut")
            .characterEncoding("uft-8")
            .session(session)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인한 대상자 아닐경우 로그아웃 불가")
    public void testSignOut_failure() throws Exception {

        mockMvc.perform(post("/users/signOut")
            .characterEncoding("uft-8")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(rslt -> assertTrue(Objects.requireNonNull(rslt.getResolvedException())
                .getClass()
                .isAssignableFrom(UserNotSignInException.class)));
    }

    @Test
    @DisplayName("인증 메일 확인 - 입력값 오류")
    void checkEmailToken_with_wrong_input() throws Exception {
        mockMvc.perform(get("/users/checkEmailToken")
            .param("token", "sdfjslwfwef")
            .param("email", "email@email.com"))
            .andExpect(rslt -> assertTrue(Objects.requireNonNull(rslt.getResolvedException())
                .getClass()
                .isAssignableFrom(TokenInvalidException.class)));
    }

    //    testdata Rollback을 위한 Transactional
    @Transactional
    @Test
    @DisplayName("인증 메일 확인 - 입력값 정상")
    void checkEmailToken() throws Exception {
        User user = new User.Builder("test@email.com", "12345678", UserType.ADMIN)
            .phone("01012341234")
            .status(Status.DEFAULT)
            .nickname("leejun")
            .build();
        user.setPassword(SHA256Util.getSHA256(user.getPassword()));
        userMapper.insertUser(user);
        User newUser = userMapper.findByEmail(user.getEmail());
        newUser.generateEmailCheckToken();

        mockMvc.perform(get("/users/checkEmailToken")
            .param("token", newUser.getEmailCheckToken())
            .param("email", newUser.getEmail()))
            .andExpect(status().isOk());
    }
}
