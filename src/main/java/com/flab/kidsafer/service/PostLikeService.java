package com.flab.kidsafer.service;

import com.flab.kidsafer.dto.PostLikeDTO;
import com.flab.kidsafer.error.exception.PostNotFoundException;
import com.flab.kidsafer.error.exception.UserNotFoundException;
import com.flab.kidsafer.mapper.PostLikeMapper;
import com.flab.kidsafer.mapper.PostMapper;
import com.flab.kidsafer.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeMapper postLikeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Transactional
    public PostLikeDTO like(int postId, int userId) {
        if (userMapper.findById(userId) == null) {
            throw new UserNotFoundException();  // TODO : Optional로 변경
        }
        if (postMapper.findPostById(postId) == null) {
            throw new PostNotFoundException();  // TODO : Optional로 변경
        }

        PostLikeDTO postLikeDTO = new PostLikeDTO.Builder()
            .setPostId(postId)
            .setUserId(userId)
            .build();
        postLikeMapper.insertPostLike(postLikeDTO);

        return postLikeDTO;
    }

    @Transactional
    public void unlike(int postId, int userId) {
        if (userMapper.findById(userId) == null) {
            throw new UserNotFoundException();  // TODO : Optional로 변경
        }
        if (postMapper.findPostById(postId) == null) {
            throw new PostNotFoundException();  // TODO : Optional로 변경
        }

        PostLikeDTO postLikeDTO = new PostLikeDTO.Builder()
            .setPostId(postId)
            .setUserId(userId)
            .build();
        postLikeMapper.deletePostLike(postLikeDTO);
    }
}
