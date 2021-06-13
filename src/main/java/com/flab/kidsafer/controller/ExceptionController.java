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
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INPUT_INVALID);

        logger.warn("MethodArgumentNotValidException 발생");
        logger.warn("에러코드: " + errorResponse.getCode() + ", 에러 상태 : " + errorResponse.getStatus());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 비즈니스 로직 관련 예외처리 클래스
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        logger.warn("BusinessException 발생");
        logger.warn("에러코드: " + errorResponse.getCode() + ", 에러 상태 : " + errorResponse.getStatus());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
