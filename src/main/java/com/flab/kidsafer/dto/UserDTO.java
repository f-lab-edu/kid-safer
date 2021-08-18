package com.flab.kidsafer.dto;

import com.flab.kidsafer.domain.enums.Status;
import com.flab.kidsafer.domain.enums.UserType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDTO {

    @NotNull(message = "Null은 포함될 수 없습니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotNull(message = "Null은 포함될 수 없습니다.")
    @Pattern(regexp = "(?=.*[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣])(?=.*[0-9])(?=.*[^\\w\\s]).{4,20}",
        message = "길이가 8~20의 알파벳, 숫자, 특수문자가 각 1개이상 포함된 어야 합니다.")
    private String password;

    @NotNull(message = "Null은 포함될 수 없습니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$", message = "길이가 3~20의 알파벳, 숫자, 한글만 허용 됩니다.")
    private String nickname;

    @Pattern(regexp = "^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$",
        message = "01로 시작하는 10-11자리 숫자여야 합니다.")
    private String phone;

    @NotNull(message = "Null은 포함될 수 없습니다.")
    private UserType type;

    private Status status;

    public UserDTO(String email, String password, String nickname, String phone,
        UserType type, Status status) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.type = type;
        this.status = status;
    }

    public UserDTO(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.nickname = builder.nickname;
        this.phone = builder.phone;
        this.type = builder.type;
        this.status = builder.status;
    }

    public static class Builder {

        private String email;
        private String password;
        private String nickname;
        private String phone;
        private UserType type;
        private Status status;

        public Builder Builder() {
            return this;
        }

        public UserDTO.Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserDTO.Builder password(String password) {
            this.password = password;
            return this;
        }
        
        public UserDTO.Builder type(UserType type) {
            this.type = type;
            return this;
        }

        public UserDTO.Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserDTO.Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserDTO.Builder status(Status status) {
            this.status = status;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhone() {
        return phone;
    }

    public UserType getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
