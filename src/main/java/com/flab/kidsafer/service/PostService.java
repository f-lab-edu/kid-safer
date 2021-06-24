package com.flab.kidsafer.service;

import com.flab.kidsafer.dto.PostDTO;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.error.exception.PostNotFoundException;
import com.flab.kidsafer.mapper.PostMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private static final Logger LOGGER = LogManager.getLogger(PostService.class);

    @Autowired
    private PostMapper postMapper;

    public PostDTO getPostInfoByPostId(int postId) {
        PostDTO post = postMapper.findPostById(postId);
        if (post == null) {
            throw new PostNotFoundException();
        }
        return post;
    }

    @Transactional
    public void registerPost(PostDTO postDTO) {
        postMapper.registerPost(postDTO);
    }

    @Transactional
    public void modifyPostsInfo(PostDTO postDTO, int userId) {
        if (postDTO.getParentId() != userId) {
            throw new OperationNotAllowedException();
        }

        postMapper.modifyPostInfo(postDTO);
    }

    @Transactional
    public void deletePosts(int postId, int userId) {
        PostDTO post = postMapper.findPostById(postId);
        if (post.getParentId() != userId) {
            throw new OperationNotAllowedException();
        }
        postMapper.deletePost(postId);
    }
}
