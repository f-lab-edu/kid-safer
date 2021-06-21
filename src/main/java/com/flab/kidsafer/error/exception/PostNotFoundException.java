package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class PostNotFoundException extends BusinessException{

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
