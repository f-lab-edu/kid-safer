package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class UserNotFoundException extends BusinessException{

  public UserNotFoundException() {
    super(ErrorCode.SIGNIN_NOT_FOUND);
  }

}
