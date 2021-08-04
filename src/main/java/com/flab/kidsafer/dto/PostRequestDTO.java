package com.flab.kidsafer.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

public class PostRequestDTO {

    private int id;

    @NotNull
    private int postId;

    @NotNull
    private int userId;

    private String contents;
    private LocalDateTime registerDate;

    public PostRequestDTO(int id, int postId,
        int userId, String contents, LocalDateTime registerDate) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.contents = contents;
        this.registerDate = registerDate;
    }

    public PostRequestDTO(PostRequestDTO.Builder builder) {
        this.id = builder.id;
        this.postId = builder.postId;
        this.userId = builder.userId;
        this.contents = builder.contents;
        this.registerDate = builder.registerDate;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public static class Builder {

        private int id;
        private int postId;
        private int userId;
        private String contents;
        private LocalDateTime registerDate;

        public Builder() {
        }

        public Builder(int id) {
            this.id = id;
        }

        public PostRequestDTO.Builder id(int id) {
            this.id = id;
            return this;
        }

        public PostRequestDTO.Builder postId(int postId) {
            this.postId = postId;
            return this;
        }

        public PostRequestDTO.Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public PostRequestDTO.Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public PostRequestDTO.Builder registerDate(LocalDateTime registerDate) {
            this.registerDate = registerDate;
            return this;
        }

        public PostRequestDTO build() {
            return new PostRequestDTO(this);
        }
    }

}
