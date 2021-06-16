package com.flab.kidsafer.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserUpdatePasswordRequest {

    @NotNull
    private int userId;

    @NotNull(message = "Null은 포함될 수 없습니다.")
    private String currentPassword;

    @NotNull(message = "Null은 포함될 수 없습니다.")
    @Pattern(regexp = "(?=.*[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣])(?=.*[0-9])(?=.*[^\\w\\s]).{4,20}",
        message = "길이가 8~20의 알파벳, 숫자, 특수문자가 각 1개이상 포함되어야 합니다.")
    private String modifiedPassword;

    public UserUpdatePasswordRequest(UserUpdatePasswordRequest.Builder builder) {
        this.userId = builder.userId;
        this.currentPassword = builder.currentPassword;
        this.modifiedPassword = builder.modifiedPassword;
    }

    public int getUserId() {
        return userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getModifiedPassword() {
        return modifiedPassword;
    }

    public static class Builder {

        private int userId;
        private String currentPassword;
        private String modifiedPassword;

        public Builder() {
        }

        public UserUpdatePasswordRequest.Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public UserUpdatePasswordRequest.Builder setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
            return this;
        }

        public UserUpdatePasswordRequest.Builder setModifiedPassword(String modifiedPassword) {
            this.modifiedPassword = modifiedPassword;
            return this;
        }

        public UserUpdatePasswordRequest build() {
            return new UserUpdatePasswordRequest(this);
        }
    }
}
