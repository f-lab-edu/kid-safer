package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class PasswordInputInvalidException extends BusinessException {

    public PasswordInputInvalidException() {
        super(ErrorCode.PASSWORD_INPUT_INVALID);
    }
}
