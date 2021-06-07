package com.flab.kidsafer.mapper;

import com.flab.kidsafer.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    int insertUser(User User);
}
