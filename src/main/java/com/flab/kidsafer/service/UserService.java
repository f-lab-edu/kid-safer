package com.flab.kidsafer.service;

import com.flab.kidsafer.config.AppProperties;
import com.flab.kidsafer.mail.EmailMessage;
import com.flab.kidsafer.domain.SignInRequest;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.dto.UserDTO;
import com.flab.kidsafer.error.exception.DuplicateIdException;
import com.flab.kidsafer.dto.UserUpdateInfoRequest;
import com.flab.kidsafer.dto.UserUpdatePasswordRequest;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.error.exception.PasswordInputInvalidException;
import com.flab.kidsafer.error.exception.UserNotAuthorizedException;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.mail.EmailService;
import com.flab.kidsafer.mapper.UserMapper;
import com.flab.kidsafer.utils.SHA256Util;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public User getUserByEmailAndPwd(String email, String cryptoPassword) {
        return userMapper.findByEmailAndPassword(email, cryptoPassword);
    }

    public User getUserByIdAndPwd(int userId, String cryptoPassword) {
        return userMapper.findByIdAndPassword(userId, cryptoPassword);
    }

    public User getUserById(int userId) {
        return userMapper.findById(userId);
    }

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private EmailService emailService;

    public User signIn(SignInRequest signInRequest) {
        LOGGER.info("signIn started");
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        String cryptoPassword = SHA256Util.getSHA256(password);

        User user = getUserByEmailAndPwd(email, cryptoPassword);

        if (user == null) {
            throw new UserNotFoundException();
        } else if ("NOT_AUTHORIZED".equals(user.getStatus())) {  // ????????? ????????? ?????? ?????? ?????????
            throw new UserNotAuthorizedException();
        }
        return user;
    }

    public User signUp(UserDTO userDto) {
        User user = saveNewUser(userDto);
        sendSignUpConfirmEmail(user);
        return user;
    }

    private User saveNewUser(@Valid UserDTO userDto) {
        User duplicationUser = userMapper.findByEmail(userDto.getEmail());

        if (duplicationUser != null) {
            throw new DuplicateIdException("????????? ??????????????????.");
        }

        userDto.setPassword(SHA256Util.getSHA256(userDto.getPassword()));
        User newUser = new User.Builder(userDto.getEmail(), userDto.getPassword(),
            userDto.getType())
            .nickname(userDto.getNickname())
            .phone(userDto.getPhone())
            .status(userDto.getStatus())
            .build();

        int insertCount = userMapper.insertUser(newUser);

        if (insertCount != 1) {
            throw new RuntimeException("???????????? ???????????? ????????? ?????????" + newUser);
        }

        return newUser;
    }

    public User finByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public User finById(int userId) {
        return userMapper.findById(userId);
    }

    public void completeSignUp(User user) {
        user.completeSignUp();
        updateEmailStatus(user);
    }

    public void updateEmailStatus(User user) {
        userMapper.updateEmailStatus(user);
    }

    public void sendSignUpConfirmEmail(User user) {
        user.generateEmailCheckToken();
        userMapper.updateEmailCheckTokenWithTime(user);
        String link = "/check_email-token?token=" + user.getEmailCheckToken()
            + "&email=" + user.getEmail();
        String message =
            "<!DOCTYPE html><html><head><title>kid-safer</title><meta charset='UTF-8'></head><body><div><div><h2>??????????????? "
                + user.getNickname()
                + "???</h2><p>????????? ????????? ??????????????? ?????? ????????? ???????????????</p><div><a href='" + appProperties.getHost()
                + link
                + "'>????????????</a><p>????????? ???????????? ?????? ???????????? ?????? URL??? ??????????????? ???????????? ?????? ????????????</p><small>"
                + appProperties.getHost() + link
                + "</small></div></div><br/><footer><small>safer&copy;2021</small></footer></div></body></html>";

        EmailMessage emailMessage = new EmailMessage.Builder()
            .to(user.getEmail())
            .subject("?????? ?????? ??????")
            .message(message)
            .build();

        emailService.sendEmail(emailMessage);
    }

    public void modifyUserInfo(UserUpdateInfoRequest userUpdateInfoRequest, int userId) {
        isSameUser(userUpdateInfoRequest.getUserId(), userId);
        userMapper.updateUserInfo(userUpdateInfoRequest);
    }

    public void changePassword(UserUpdatePasswordRequest userUpdatePasswordRequest,
        int userId) {
        isSameUser(userUpdatePasswordRequest.getUserId(), userId);

        User user = getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }

        String cryptCurPwd = SHA256Util.getSHA256(userUpdatePasswordRequest.getCurrentPassword());
        if (!user.getPassword().equals(cryptCurPwd)) {
            throw new PasswordInputInvalidException();
        }

        String cryptPwd = SHA256Util.getSHA256(userUpdatePasswordRequest.getModifiedPassword());
        userMapper.updateUserPassword(userId, cryptPwd);
    }

    public void isSameUser(int requestUserId, int sessionUserId) {
        if (requestUserId != sessionUserId) {
            throw new OperationNotAllowedException();
        }
    }
}
