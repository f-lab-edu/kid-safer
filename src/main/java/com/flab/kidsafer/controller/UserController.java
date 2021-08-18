package com.flab.kidsafer.controller;

import com.flab.kidsafer.aop.CheckLogin;
import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.SignInResponse;
import com.flab.kidsafer.domain.enums.SignInStatus;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.dto.UserDTO;
import com.flab.kidsafer.dto.UserUpdateInfoRequest;
import com.flab.kidsafer.dto.UserUpdatePasswordRequest;
import com.flab.kidsafer.error.exception.EmailSendTimeException;
import com.flab.kidsafer.error.exception.TokenInvalidException;
import com.flab.kidsafer.error.exception.UserNotSignUpException;
import com.flab.kidsafer.service.UserService;
import com.flab.kidsafer.utils.SessionUtil;
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
import org.springframework.web.servlet.ModelAndView;

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

        SessionUtil.setLoginUserId(httpSession, user);

        signInResponse = new SignInResponse(SignInStatus.SUCCESS, user);
        responseEntity = new ResponseEntity<>(signInResponse, HttpStatus.OK);

        return responseEntity;
    }

    @CheckLogin
    @PostMapping("/signOut")
    public void signOut(HttpSession httpSession) {
        SessionUtil.logoutUser(httpSession);
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody UserDTO userDto, ModelAndView mav) {
        User inserUser = userService.signUp(userDto);

        if (inserUser == null) {
            throw new UserNotSignUpException();
        }

        SignInRequest signInRequest = new SignInRequest.Builder(userDto.getEmail(),
            userDto.getPassword())
            .build();

        userService.signIn(signInRequest);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/confirmEmail")
    public ResponseEntity<User> confirmEmail(HttpSession httpSession, ModelAndView mav) {

        int userId = SessionUtil.getLoginUserId(httpSession);
        User user = userService.finById(userId);

        if (!user.canSendConfirmEmail()) {
            throw new EmailSendTimeException();
        }

        userService.sendSignUpConfirmEmail(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/checkEmailToken")
    public ResponseEntity<User> checkEamilToken(String token, String email) {
        User user = userService.finByEmail(email);

        if (user == null || !user.isValidToken(token)) {
            throw new TokenInvalidException();
        }

        userService.completeSignUp(user);

        return ResponseEntity.ok(user);
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
}
