package com.flab.kidsafer.controller;

import com.flab.kidsafer.domain.LoginRequest;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.flab.kidsafer.commons.HttpStatusResponseEntity.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final String MEMBER_ID = "MEMBER_ID";

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<HttpStatus> signUp(@RequestBody @Valid LoginRequest loginRequest, HttpSession httpSession) {

        User user = userService.signUp(loginRequest);

        if (user != null) {
            httpSession.setAttribute(MEMBER_ID, user.getUserId());
            return RESPONSE_OK;
        }
        return RESPONSE_BAD_REQUEST;
    }
}
