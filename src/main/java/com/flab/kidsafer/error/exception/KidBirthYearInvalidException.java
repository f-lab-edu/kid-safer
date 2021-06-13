package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class KidBirthYearInvalidException extends BusinessException {

    public KidBirthYearInvalidException() {
        super(ErrorCode.KID_INPUT_BIRTHYEAR_INVALID);
    }

}
