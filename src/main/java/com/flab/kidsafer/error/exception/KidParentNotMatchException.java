package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class KidParentNotMatchException extends BusinessException {

    public KidParentNotMatchException() {
        super(ErrorCode.KID_PARENT_NOT_MATCH);
    }
}
