package com.flab.kidsafer.dto;

import javax.validation.constraints.NotNull;

public class PostLikeDTO {

    @NotNull
    private int id;
    @NotNull
    private int postId;
    @NotNull
    private int userId;

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public PostLikeDTO(PostLikeDTO.Builder builder) {
        this.id = builder.id;
        this.postId = builder.postId;
        this.userId = builder.userId;
    }

    public static class Builder {

        private int id;
        private int postId;
        private int userId;

        public Builder() {
        }

        public PostLikeDTO.Builder setId(int id) {
            this.id = id;
            return this;
        }

        public PostLikeDTO.Builder setPostId(int postId) {
            this.postId = postId;
            return this;
        }

        public PostLikeDTO.Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public PostLikeDTO build() {
            return new PostLikeDTO(this);
        }
    }
}
