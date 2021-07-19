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

    public PostCommentDTO(PostCommentDTO.Builder builder) {
        this.commentLevel = builder.commentLevel;
        this.postId = builder.postId;
        this.commentId = builder.commentId;
        this.commentParentId = builder.commentParentId;
        this.commentContent = builder.commentContent;
        this.commentWriterId = builder.commentWriterId;
        this.commentCreateDate = builder.commentCreateDate;
        this.commentModDate = builder.commentModDate;
        this.role = builder.role;
        this.commentStatus = builder.commentStatus;
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

    public static class Builder {

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

        public Builder() {
        }

        public PostCommentDTO.Builder commentLevel(int commentLevel) {
            this.commentLevel = commentLevel;
            return this;
        }

        public PostCommentDTO.Builder postId(int postId) {
            this.postId = postId;
            return this;
        }

        public PostCommentDTO.Builder commentId(int commentId) {
            this.postId = commentId;
            return this;
        }

        public PostCommentDTO.Builder commentParentId(int commentParentId) {
            this.postId = commentParentId;
            return this;
        }

        public PostCommentDTO.Builder commentContent(String commentContent) {
            this.commentContent = commentContent;
            return this;
        }

        public PostCommentDTO.Builder commentWriterId(int commentWriterId) {
            this.commentWriterId = commentWriterId;
            return this;
        }

        public PostCommentDTO.Builder role(String role) {
            this.role = role;
            return this;
        }

        public PostCommentDTO.Builder commentStatus(String commentStatus) {
            this.commentStatus = commentStatus;
            return this;
        }

        public PostCommentDTO build() {
            return new PostCommentDTO(this);
        }
    }
}
