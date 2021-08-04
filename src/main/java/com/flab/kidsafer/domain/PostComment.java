/**
 *
 */
package com.flab.kidsafer.domain;

import com.flab.kidsafer.dto.PostCommentDTO;
import java.time.LocalDateTime;

public class PostComment {

    private int commentLevel;
    private int postId;
    private int commentId;
    private int commentParentId;
    private String commentContent;
    private int commentWriterId;
    private LocalDateTime commentCreateDate;
    private LocalDateTime commentModDate;
    private String role;
    private String commentStatus;

    public PostComment(int commentLevel, int postId, int commentId, int commentParentId,
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

    public PostComment(PostCommentDTO postCommentDTO) {
        this.commentLevel = postCommentDTO.getCommentLevel();
        this.postId = postCommentDTO.getPostId();
        this.commentId = postCommentDTO.getCommentId();
        this.commentParentId = postCommentDTO.getCommentParentId();
        this.commentContent = postCommentDTO.getCommentContent();
        this.commentWriterId = postCommentDTO.getCommentWriterId();
        this.commentCreateDate = postCommentDTO.getCommentCreateDate();
        this.commentModDate = postCommentDTO.getCommentModDate();
        this.role = postCommentDTO.getRole();
        this.commentStatus = postCommentDTO.getCommentStatus();
    }

    public int getCommentLevel() {
        return commentLevel;
    }

    public int getPostId() {
        return postId;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getCommentParentId() {
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

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void setCommentWriterId(int commentWriterId) {
        this.commentWriterId = commentWriterId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCommentLevel(int commentLevel) {
        this.commentLevel = commentLevel;
    }

    public boolean checkSameId(int userId) {
        return commentWriterId == userId;
    }
}
