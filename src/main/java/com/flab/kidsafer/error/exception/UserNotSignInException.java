package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class UserNotSignInException extends BusinessException{

  public UserNotSignInException() {
    super(ErrorCode.USER_NOT_SIGNIN);
  }
}
