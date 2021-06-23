package com.flab.kidsafer.mapper;

import com.flab.kidsafer.dto.PostLikeDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostLikeMapper {

    boolean hasPostLikeByPostIdAndUserId(int postId, int userId);

    void insertPostLike(PostLikeDTO postLike);

    void deletePostLike(PostLikeDTO postLike);

}
