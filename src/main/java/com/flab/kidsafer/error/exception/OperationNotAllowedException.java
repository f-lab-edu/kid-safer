package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class OperationNotAllowedException extends BusinessException {

    public OperationNotAllowedException() {
        super(ErrorCode.OPERATION_NOT_ALLOWED);
    }
}
