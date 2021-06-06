package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class EmailSendTimeException extends BusinessException {

    public EmailSendTimeException() {
        super(ErrorCode.EMAIL_SEND_TIME);
    }
}
