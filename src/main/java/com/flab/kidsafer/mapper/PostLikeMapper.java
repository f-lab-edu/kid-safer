package com.flab.kidsafer.mapper;

import com.flab.kidsafer.domain.PostLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostLikeMapper {

    boolean hasPostLikeByPostIdAndUserId(int postId, int userId);

    void insertPostLike(PostLike postLike);

    void deletePostLike(PostLike postLike);

}
