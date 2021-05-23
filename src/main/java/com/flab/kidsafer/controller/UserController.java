package com.flab.kidsafer.controller;

import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.SignInResponse;
import com.flab.kidsafer.domain.SignInStatus;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String MEMBER_ID = "MEMBER_ID";

    @Autowired
    private UserService userService;

    @PostMapping("/signIn")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest,
        HttpSession httpSession) {

        User user = userService.signIn(signInRequest);
        SignInResponse signInResponse;
        ResponseEntity<SignInResponse> responseEntity;

        httpSession.setAttribute(MEMBER_ID, user.getUserId());
        signInResponse = new SignInResponse(SignInStatus.SUCCESS, user);
        responseEntity = new ResponseEntity<>(signInResponse, HttpStatus.OK);

        return responseEntity;
    }
}
