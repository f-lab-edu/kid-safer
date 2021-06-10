package com.flab.kidsafer.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Post {

    private int id;
    private int parentId;
    private int districtId;
    private String title;
    private String contents;
    private int fee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime registerDate;
    private LocalDateTime dueDate;
    private boolean isFinished;

    public Post(Post.Builder builder) {
        this.id = builder.id;
        this.parentId = builder.parentId;
        this.districtId = builder.districtId;
        this.title = builder.title;
        this.contents = builder.contents;
        this.fee = builder.fee;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.registerDate = builder.registerDate;
        this.dueDate = builder.dueDate;
        this.isFinished = builder.isFinished;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public int getFee() {
        return fee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public static class Builder {

        private int id;
        private int parentId;
        private int districtId;
        private String title;
        private String contents;
        private int fee;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalDateTime registerDate;
        private LocalDateTime dueDate;
        private boolean isFinished;

        public Builder(int id, int parentId, int districtId, String title, String contents, int fee,
            LocalDate startDate, LocalDate endDate, LocalDateTime registerDate,
            LocalDateTime dueDate, boolean isFinished) {
            this.id = id;
            this.parentId = parentId;
            this.districtId = districtId;
            this.title = title;
            this.contents = contents;
            this.fee = fee;
            this.startDate = startDate;
            this.endDate = endDate;
            this.registerDate = registerDate;
            this.dueDate = dueDate;
            this.isFinished = isFinished;
        }

        public Post.Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Post.Builder setParentId(int parentId) {
            this.parentId = parentId;
            return this;
        }

        public Post.Builder setDistrictId(int districtId) {
            this.districtId = districtId;
            return this;
        }

        public Post.Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Post.Builder setContents(String contents) {
            this.contents = contents;
            return this;
        }

        public Post.Builder setFee(int fee) {
            this.fee = fee;
            return this;
        }

        public Post.Builder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Post.Builder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Post.Builder setRegisterDate(LocalDateTime registerDate) {
            this.registerDate = registerDate;
            return this;
        }

        public Post.Builder setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Post.Builder setFinished(boolean finished) {
            isFinished = finished;
            return this;
        }

        public Post builder() {
            return new Post(this);
        }
    }
}
