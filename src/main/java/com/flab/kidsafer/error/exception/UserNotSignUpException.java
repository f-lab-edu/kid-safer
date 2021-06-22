package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class UserNotSignUpException extends BusinessException{

  public UserNotSignUpException() {
    super(ErrorCode.USER_NOT_SIGNUP);
  }
}
