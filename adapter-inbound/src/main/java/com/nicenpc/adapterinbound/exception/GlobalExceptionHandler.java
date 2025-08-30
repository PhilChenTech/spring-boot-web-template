package com.nicenpc.adapterinbound.exception;

import com.nicenpc.domain.exception.DomainException;
import com.nicenpc.domain.exception.UserValidationException;
import com.nicenpc.domain.exception.UserNotFoundException;
import com.nicenpc.domain.exception.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局異常處理器
 * 統一處理應用程式中的異常，提供結構化的錯誤響應
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    // 使用者相關異常處理
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request) {
        
        logger.warn("使用者不存在: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.USER_NOT_FOUND,
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            getPath(request)
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, WebRequest request) {
        
        logger.warn("使用者已存在: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.USER_ALREADY_EXISTS,
            ex.getMessage(),
            HttpStatus.CONFLICT.value(),
            getPath(request)
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserValidationException(
            UserValidationException ex, WebRequest request) {
        
        logger.warn("使用者驗證失敗: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.USER_VALIDATION_ERROR,
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            getPath(request)
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(
            DomainException ex, WebRequest request) {
        
        logger.warn("領域異常: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.BUSINESS_RULE_VIOLATION,
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            getPath(request)
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    // 系統異常處理
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        logger.warn("非法參數: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.BAD_REQUEST,
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            getPath(request)
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });
        
        logger.warn("驗證失敗: {}", fieldErrors);
        
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.VALIDATION_ERROR,
            "請求資料驗證失敗",
            HttpStatus.BAD_REQUEST.value(),
            getPath(request)
        );
        errorResponse.setAdditionalInfo(Map.of("fieldErrors", fieldErrors));
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        
        logger.error("運行時異常: ", ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.INTERNAL_SERVER_ERROR,
            "系統運行時發生異常",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            getPath(request)
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        logger.error("未處理異常: ", ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.INTERNAL_SERVER_ERROR,
            "系統發生未預期的錯誤",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            getPath(request)
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    private String getPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
