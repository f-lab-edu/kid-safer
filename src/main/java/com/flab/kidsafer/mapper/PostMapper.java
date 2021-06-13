package com.flab.kidsafer.mapper;

import com.flab.kidsafer.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

    PostDTO findPostById(int postId);

    void registerPost(PostDTO postDTO);

    void modifyPostInfo(PostDTO postDTO);

    void deletePost(int postId);

}
