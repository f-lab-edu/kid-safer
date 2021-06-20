package com.flab.kidsafer.controller;

import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.SignInResponse;
import com.flab.kidsafer.domain.SignInStatus;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.domain.UserDto;
import com.flab.kidsafer.error.exception.UserNotSignInException;
import com.flab.kidsafer.dto.UserUpdateInfoRequest;
import com.flab.kidsafer.dto.UserUpdatePasswordRequest;
import com.flab.kidsafer.service.UserService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        SessionUser sessionUser = new SessionUser(user);
        httpSession.setAttribute("user", sessionUser);
        signInResponse = new SignInResponse(SignInStatus.SUCCESS, user);
        responseEntity = new ResponseEntity<>(signInResponse, HttpStatus.OK);

        return responseEntity;
    }

    @PostMapping("/signOut")
    public void signOut(HttpSession httpSession) {
        if (!isSignIn(httpSession)) {
            throw new UserNotSignInException();
        }
        httpSession.removeAttribute(MEMBER_ID);

    }

    public boolean isSignIn(final HttpSession httpSession) {
        return httpSession.getAttribute(MEMBER_ID) != null;
    }

    @PostMapping("/signUp")
    public void signUp(@RequestBody UserDto userDto) {
        userService.signUp(userDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping("/{userId}")
    public void modifyUserInfo(@PathVariable int userId,
        @Valid @RequestBody UserUpdateInfoRequest userUpdateInfoRequest,
        HttpSession httpSession) {
        userService.modifyUserInfo(userUpdateInfoRequest, userId);
    }

    @PutMapping("/{userId}/password")
    public void changeUserPassword(@PathVariable int userId,
        @Valid @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
        HttpSession httpSession) {
        userService.changePassword(userUpdatePasswordRequest, userId);
    }

    public int getSessionUserId(HttpSession httpSession) {
        return (int) httpSession.getAttribute("MEMBER_ID");
    }
}
