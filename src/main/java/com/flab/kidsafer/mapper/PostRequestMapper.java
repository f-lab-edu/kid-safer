package com.flab.kidsafer.mapper;

import com.flab.kidsafer.dto.PostRequestDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostRequestMapper {

    List<PostRequestDTO> getAllRequests(int postId);

    PostRequestDTO getSingleRequest(int postId, int userId);

    void applyRequest(PostRequestDTO postRequestDTO);

    void cancelRequest(int postId, int userId);

}
