package com.flab.kidsafer.controller;

import com.flab.kidsafer.error.ErrorCode;
import com.flab.kidsafer.error.ErrorResponse;
import com.flab.kidsafer.error.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LogManager.getLogger(ExceptionController.class);

    /**
     * 어노테이션 @valid 유효성 체크에 통과하지 못하면 MethodArgumentNotValidException 이 발생한다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e) {
        logger.warn("MethodArgumentNotValidException 발생");
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.SIGNIN_INPUT_INVALID);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 비즈니스 로직 관련 예외처리 클래스
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        logger.warn("BusinessException 발생");
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
