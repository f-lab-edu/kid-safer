package com.flab.kidsafer.mapper;

import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.User;
import com.flab.kidsafer.dto.UserUpdateInfoRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findById(int userId);

    User findByIdAndPassword(int userId, String password);

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    int insertUser(User User);

    void updateUserInfo(UserUpdateInfoRequest userUpdateInfoRequest);

    void updateUserPassword(int userId, String password);

    void updateEmailCheckTokenWithTime(User user);

    void updateEmailStatus(User user);

    void updateUserStatus(int userId, Status status);
}
