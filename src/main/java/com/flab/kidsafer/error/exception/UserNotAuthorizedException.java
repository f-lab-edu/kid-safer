package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class UserNotAuthorizedException extends BusinessException {

  public UserNotAuthorizedException() {
    super(ErrorCode.USER_NOT_AUTHORIZED);
  }
}
