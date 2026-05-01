package org.sopt.exception;

import org.sopt.dto.response.BaseResponse;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Void>> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(BaseResponse.fail(
                        errorCode.getCode(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        ErrorCode errorCode = extractErrorCode(exception);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(BaseResponse.fail(
                        errorCode.getCode(),
                        errorCode.getMessage()
                ));
    }

    private ErrorCode extractErrorCode(MethodArgumentNotValidException exception) {
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            String errorCodeName = fieldError.getDefaultMessage();

            if (errorCodeName == null || errorCodeName.isBlank()) {
                continue;
            }

            try {
                return ErrorCode.valueOf(errorCodeName);
            } catch (IllegalArgumentException e) {
                return ErrorCode.INVALID_REQUEST_BODY;
            }
        }

        return ErrorCode.INVALID_REQUEST_BODY;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<Void>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception
    ) {
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST_PARAMETER;

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(BaseResponse.fail(
                        errorCode.getCode(),
                        errorCode.getMessage()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception
    ) {
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST_BODY;

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(BaseResponse.fail(
                        errorCode.getCode(),
                        errorCode.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception exception) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(BaseResponse.fail(
                        errorCode.getCode(),
                        errorCode.getMessage()
                ));
    }
}
