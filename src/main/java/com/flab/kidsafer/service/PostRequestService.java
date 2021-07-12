package com.flab.kidsafer.service;

import com.flab.kidsafer.dto.PostRequestDTO;
import com.flab.kidsafer.mapper.PostMapper;
import com.flab.kidsafer.mapper.PostRequestMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostRequestService {

    @Autowired
    private PostRequestMapper postRequestMapper;

    @Autowired
    private PostMapper postMapper;

    public List<PostRequestDTO> getAllRequests(int postId) {
        return postRequestMapper.getAllRequests(postId);
    }

    public PostRequestDTO getSingleRequest(int postId, int userId) {
        return postRequestMapper.getSingleRequest(postId, userId);
    }


    public void applyRequest(PostRequestDTO postRequestDTO) {
        postRequestMapper.applyRequest(postRequestDTO);
    }

    public void cancelRequest(int postId, int userId) {
        postRequestMapper.cancelRequest(postId, userId);
    }

}
