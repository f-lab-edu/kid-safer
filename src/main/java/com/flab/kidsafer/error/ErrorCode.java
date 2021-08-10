package com.flab.kidsafer.error;

public enum ErrorCode {
    // Common
    INPUT_INVALID(400, "C001", "입력 값이 유효하지 않습니다"),

    // Common
    OPERATION_NOT_ALLOWED(400, "C001", "유효하지 않은 접근입니다."),

    // User
    SIGNIN_INPUT_INVALID(400, "M001", "로그인시 입력한 값이 유효하지 않습니다."),
    SIGNIN_NOT_FOUND(400, "M002", "이메일 혹은 비밀번호를 잘못 입력하였습니다."),
    USER_NOT_AUTHORIZED(400, "M003", "이메일 인증을 받아야 로그인이 가능합니다."),
    USER_NOT_SIGNIN(401, "M004", "로그인을 하지 않은 사용자입니다."),
    USER_NOT_FOUND(404, "M005", "존재하지 않는 대상자입니다."),
    PASSWORD_INPUT_INVALID(400, "M006", "비밀번호가 기존 값과 일치하지 않습니다."),
    TOKEN_INPUT_INVALID(400, "M007", "유저 혹은 토큰이 유효하지 않습니다."),
    EMAIL_SEND_TIME(400, "M008", "이메일은 1시간에 한번만 보낼수 있습니다."),
    USER_NOT_SIGNUP(400, "M009", "가입에 실패 하였습니다."),

    // Kid
    KID_INPUT_BIRTHYEAR_INVALID(400, "K001", "출생년도가 유효하지 않습니다."),
    KID_NOT_FOUND(400, "K002", "해당하는 아이가 없습니다."),
    KID_PARENT_NOT_MATCH(400, "K003", "아이의 부모가 아닙니다."),

    // Post
    POST_NOT_FOUND(404, "P001", "해당하는 게시글이 없습니다"),

    // Comment
    COMMENT_NOT_FOUND(404, "CM001", "해당하는 댓글이 없습니다");

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
