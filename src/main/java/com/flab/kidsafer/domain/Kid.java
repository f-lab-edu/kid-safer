package com.flab.kidsafer.domain;

import com.flab.kidsafer.annotation.Gender;
import com.flab.kidsafer.annotation.Minor;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Kid {

    @NotNull
    private int id;
    @NotNull
    private int parentId;
    @NotNull
    @Gender
    private String gender;
    @NotNull
    @Pattern(regexp = "^[0-9]*${4}")
    @Minor
    private String birthYear;
    private String detail;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

    public Kid(int id, int parentId,
        String gender, String birthYear, String detail,
        LocalDateTime createdDate, LocalDateTime lastUpdatedDate) {
        this.id = id;
        this.parentId = parentId;
        this.gender = gender;
        this.birthYear = birthYear;
        this.detail = detail;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kid kid = (Kid) o;
        return id == kid.id && parentId == kid.parentId && gender.equals(kid.gender) && birthYear
            .equals(kid.birthYear) && Objects.equals(detail, kid.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, gender, birthYear, detail);
    }

}
