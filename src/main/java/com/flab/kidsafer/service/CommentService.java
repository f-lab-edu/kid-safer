package com.flab.kidsafer.service;

import static java.util.stream.Collectors.toList;

import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.PostComment;
import com.flab.kidsafer.dto.PostCommentDTO;
import com.flab.kidsafer.error.exception.CommentNotFoundException;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.mapper.CommentMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private static final int EMPTY_WRITER = -1;

    @Autowired
    private CommentMapper commentMapper;

    public List<PostCommentDTO> getCommentsByPostId(int postId, int userId) {
        List<PostComment> comments = commentMapper.selectCommentsByPostId(postId);

        //댓글을 작성한 사람이라면 수정 삭제가 가능해야하기 때문에 그 부분을 검증한다.
        for (PostComment comment : comments) {
            if (comment.getCommentWriterId() == userId) {
                comment.setRole("writer");
            }
        }

        return comments.stream()
            .map(PostCommentDTO::new)
            .collect(toList());
    }

    public int saveNewComments(PostCommentDTO comment, SessionUser user) {
        if (user == null) {
            throw new OperationNotAllowedException();
        }

        PostComment curComment = new PostComment(comment);
        commentMapper.insertNewComment(curComment);

        return curComment.getCommentId();
    }

    public void deleteComment(int commentId, int userId) {
        PostComment postComment = commentMapper.selectCommentById(commentId);

        if (postComment == null) {
            throw new CommentNotFoundException();
        }

        if (!postComment.checkSameId(userId)) {
            throw new OperationNotAllowedException();
        }

        int count = commentMapper.getCountRelatedComments(commentId);

        if (count == 0) {
            commentMapper.deleteComment(commentId);
        } else {
            PostComment comment = commentMapper.selectCommentById(commentId);
            comment.setCommentContent("원 댓글이 삭제된 게시물입니다.");
            comment.setCommentWriterId(EMPTY_WRITER);
            commentMapper.updateDisableComment(comment);
        }
    }

    public void updateComment(PostCommentDTO comment, int userId) {
        PostComment postComment = commentMapper.selectCommentById(comment.getCommentId());

        if (postComment == null) {
            throw new CommentNotFoundException();
        }

        if (!postComment.checkSameId(userId)) {
            throw new OperationNotAllowedException();
        }

        postComment.setCommentContent(comment.getCommentContent());

        commentMapper.updateComment(postComment);
    }
}
