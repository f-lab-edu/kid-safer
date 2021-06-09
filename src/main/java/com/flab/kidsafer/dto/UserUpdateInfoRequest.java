package com.flab.kidsafer.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserUpdateInfoRequest {

    @NotNull
    private int userId;

    @NotNull(message = "Null은 포함될 수 없습니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$", message = "길이가 3~20의 알파벳, 숫자, 한글만 허용 됩니다.")
    private String nickname;

    @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$",
        message = "01로 시작하는 10-11자리 숫자여야 합니다.")
    private String phone;

    public UserUpdateInfoRequest(UserUpdateInfoRequest.Builder builder) {
        this.userId = builder.userId;
        this.nickname = builder.nickname;
        this.phone = builder.phone;
    }

    public int getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhone() {
        return phone;
    }

    public static class Builder {

        private int userId;
        private String nickname;
        private String phone;

        public Builder() {
        }

        public UserUpdateInfoRequest.Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public UserUpdateInfoRequest.Builder setNickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserUpdateInfoRequest.Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserUpdateInfoRequest build() {
            return new UserUpdateInfoRequest(this);
        }
    }
}
