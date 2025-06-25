package org.sopt.bofit.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.response.BaseErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.sopt.bofit.global.exception.constant.GlobalErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseErrorResponse handleInternalServerError(Exception e) {
        log.error("Unhandled exception occurred: {}", e.getMessage(), e);
        return BaseErrorResponse.of(INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseErrorResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.warn("No handler found for URI: {}", e.getRequestURL());
        return BaseErrorResponse.of(NOT_FOUND_PATH);
    }


    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public BaseErrorResponse handleMethodNotAllowedException(MethodNotAllowedException e) {
        log.warn("Method not allowed: {}", e.getMessage());
        return BaseErrorResponse.of(METHOD_NOT_ALLOWED);
    }

    //커스텀 예외
    @ExceptionHandler(CustomException.class)
    public BaseErrorResponse handleCustomException(CustomException e) {
        log.warn("CustomException: code={}, message={}, detail={}",
                e.getErrorCode().getHttpStatus(), e.getMessage(), e.getMessageDetail());
        return BaseErrorResponse.of(e.getErrorCode().getHttpStatus(), e.getMessage(), e.getMessageDetail());
    }

    //입력값 검증할 때 발생하는 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("입력값이 잘못되었습니다.");

        log.warn("Validation failed: {}", message);
        return BaseErrorResponse.of(BAD_REQUEST.value(),message, null);
    }

    // JSON 파싱 오류
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseErrorResponse handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("Invalid JSON Format: {}", e.getMessage());
        return BaseErrorResponse.of(INVALID_JSON);
    }

    // 요청 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseErrorResponse handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        log.warn("Missing request parameter: {}", e.getParameterName());
        return BaseErrorResponse.of(MISSING_REQUEST_PARAMETER);
    }

    // 타입 불일치
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseErrorResponse handleTypeMismatch(TypeMismatchException e) {
        log.warn("Type mismatch for parameter: {}", e.getPropertyName());
        return BaseErrorResponse.of(TYPE_MISMATCH);
    }

    // 경로 변수 누락
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseErrorResponse handleMissingPathVariable(MissingPathVariableException e) {
        log.warn("Missing path variable: {}", e.getVariableName());
        return BaseErrorResponse.of(MISSING_PATH_VARIABLE);
    }

}
