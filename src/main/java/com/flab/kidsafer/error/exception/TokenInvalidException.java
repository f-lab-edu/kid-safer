package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class TokenInvalidException extends BusinessException {

    public TokenInvalidException() {
        super(ErrorCode.TOKEN_INPUT_INVALID);
    }
}
