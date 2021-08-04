package com.flab.kidsafer.error.exception;

import com.flab.kidsafer.error.ErrorCode;

public class CommentNotFoundException extends BusinessException{

    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
