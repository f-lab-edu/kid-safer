package com.flab.kidsafer.mapper;

import com.flab.kidsafer.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findByIdAndPassword(String id, String password);
}