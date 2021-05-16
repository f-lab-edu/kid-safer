package com.flab.kidsafer.error;

public enum ErrorCode {

  // User
  SIGNIN_INPUT_INVALID(400, "M001", "로그인시 입력한 값이 유효하지 않습니다.");

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