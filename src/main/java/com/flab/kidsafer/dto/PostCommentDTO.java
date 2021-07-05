/**
 *
 */
package com.flab.kidsafer.dto;

import com.flab.kidsafer.domain.PostComment;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

public class PostCommentDTO {

    private int commentLevel;
    @NotNull
    private int postId;
    @NotNull
    private int commentId;
    private int commentParentId;

    @NotNull
    private String commentContent;

    @NotNull
    private int commentWriterId;
    private LocalDateTime commentCreateDate;
    private LocalDateTime commentModDate;
    private String role;
    private String commentStatus;

    public PostCommentDTO(int commentLevel, int postId, int commentId, int commentParentId,
        String commentContent, int commentWriterId, LocalDateTime commentCreateDate,
        LocalDateTime commentModDate, String role, String commentStatus) {
        this.commentLevel = commentLevel;
        this.postId = postId;
        this.commentId = commentId;
        this.commentParentId = commentParentId;
        this.commentContent = commentContent;
        this.commentWriterId = commentWriterId;
        this.commentCreateDate = commentCreateDate;
        this.commentModDate = commentModDate;
        this.role = role;
        this.commentStatus = commentStatus;
    }

    public PostCommentDTO(PostComment postComment) {
        this.commentLevel = postComment.getCommentLevel();
        this.postId = postComment.getPostId();
        this.commentId = postComment.getCommentId();
        this.commentParentId = postComment.getCommentParentId();
        this.commentContent = postComment.getCommentContent();
        this.commentWriterId = postComment.getCommentWriterId();
        this.commentCreateDate = postComment.getCommentCreateDate();
        this.commentModDate = postComment.getCommentModDate();
        this.role = postComment.getRole();
        this.commentStatus = postComment.getCommentStatus();
    }

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public Integer getCommentParentId() {
        return commentParentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public int getCommentWriterId() {
        return commentWriterId;
    }

    public LocalDateTime getCommentCreateDate() {
        return commentCreateDate;
    }

    public LocalDateTime getCommentModDate() {
        return commentModDate;
    }

    public String getRole() {
        return role;
    }

    public String getCommentStatus() {
        return commentStatus;
    }
}
