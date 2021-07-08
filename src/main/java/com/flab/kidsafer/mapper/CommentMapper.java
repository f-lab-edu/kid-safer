package com.flab.kidsafer.mapper;

import com.flab.kidsafer.domain.PostComment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    List<PostComment> selectCommentsByPostId(int postId);

    PostComment selectCommentById(int commentId);

    int getCountRelatedComments(int commentId);

    void insertNewComment(PostComment comment);

    void deleteComment(int commentId);

    void updateDisableComment(PostComment comment);

    void updateComment(PostComment postComment);
}
