package com.flab.kidsafer.domain;

import com.flab.kidsafer.dto.PostLikeDTO;

public class PostLike {

    private int id;
    private int postId;
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

    public PostLike(Builder builder) {
        this.id = builder.id;
        this.postId = builder.postId;
        this.userId = builder.userId;
    }

    public PostLikeDTO toPostLikeDTO() {
        return new PostLikeDTO.Builder().setId(this.id).setPostId(this.postId)
            .setUserId(this.userId)
            .build();
    }

    public static class Builder {

        private int id;
        private int postId;
        private int userId;

        public Builder() {
        }

        public PostLike.Builder id(int id) {
            this.id = id;
            return this;
        }

        public PostLike.Builder postId(int postId) {
            this.postId = postId;
            return this;
        }

        public PostLike.Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public PostLike build() {
            return new PostLike(this);
        }
    }
}
