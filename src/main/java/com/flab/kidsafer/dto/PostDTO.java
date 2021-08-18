package com.flab.kidsafer.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PostDTO implements Serializable {

    @NotNull
    private int id;
    @NotNull
    private int parentId;
    @NotNull
    private int districtId;
    @NotNull
    private String title;
    private String contents;
    @Min(0)
    private int fee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime registerDate;
    private LocalDateTime dueDate;
    private boolean isFinished;

    public PostDTO(int id, int parentId, int districtId, String title, String contents, int fee,
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

    public PostDTO(PostDTO.Builder builder) {
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

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
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

        public Builder() {

        }

        public Builder(int id) {
            this.id = id;
        }

        public PostDTO.Builder setId(int id) {
            this.id = id;
            return this;
        }

        public PostDTO.Builder setParentId(int parentId) {
            this.parentId = parentId;
            return this;
        }

        public PostDTO.Builder setDistrictId(int districtId) {
            this.districtId = districtId;
            return this;
        }

        public PostDTO.Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public PostDTO.Builder setContents(String contents) {
            this.contents = contents;
            return this;
        }

        public PostDTO.Builder setFee(int fee) {
            this.fee = fee;
            return this;
        }

        public PostDTO.Builder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public PostDTO.Builder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public PostDTO.Builder setRegisterDate(LocalDateTime registerDate) {
            this.registerDate = registerDate;
            return this;
        }

        public PostDTO.Builder setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public PostDTO.Builder setFinished(boolean finished) {
            isFinished = finished;
            return this;
        }

        public PostDTO build() {
            return new PostDTO(this);
        }
    }
}
