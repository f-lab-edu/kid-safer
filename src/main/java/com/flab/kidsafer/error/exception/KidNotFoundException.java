package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class KidNotFoundException extends BusinessException {

    public KidNotFoundException() {
        super(ErrorCode.KID_NOT_FOUND);
    }
}
