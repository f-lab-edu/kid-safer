package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class SignInNotFoundException extends BusinessException {

    public SignInNotFoundException() {
        super(ErrorCode.SIGNIN_NOT_FOUND);
    }
}
