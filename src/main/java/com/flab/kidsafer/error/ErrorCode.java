package com.flab.kidsafer.error;

public enum ErrorCode {
    // Common
    INPUT_INVALID(400, "C001", "입력 값이 유효하지 않습니다"),

    // User
    SIGNIN_INPUT_INVALID(400, "M001", "로그인시 입력한 값이 유효하지 않습니다."),
    SIGNIN_NOT_FOUND(400, "M002", "이메일 혹은 비밀번호를 잘못 입력하였습니다."),
    USER_NOT_AUTHORIZED(400, "M003", "이메일 인증을 받아야 로그인이 가능합니다."),

    // Kid
    KID_INPUT_BIRTHYEAR_INVALID(400, "K001", "출생년도가 유효하지 않습니다.");


    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}