package com.nicenpc.adapter.inbound.exception;

import com.nicenpc.adapter.inbound.dto.ApiResponse;
import com.nicenpc.domain.exception.DomainException;
import com.nicenpc.domain.exception.UserValidationException;
import com.nicenpc.domain.exception.UserNotFoundException;
import com.nicenpc.domain.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 全域異常處理器
 *
 * <p>統一處理應用程式中的所有異常，提供一致的錯誤回應格式。
 * 遵循異常處理的最佳實踐和規範。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * 處理使用者不存在異常
     *
     * @param ex 使用者不存在異常
     * @return 404 錯誤回應
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(ex.getMessage()));
    }
    
    /**
     * 處理使用者已存在異常
     *
     * @param ex 使用者已存在異常
     * @return 409 錯誤回應
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        log.warn("User already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ApiResponse.error(ex.getMessage()));
    }
    
    /**
     * 處理使用者驗證異常
     *
     * @param ex 使用者驗證異常
     * @return 400 錯誤回應
     */
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserValidation(UserValidationException ex) {
        log.warn("User validation failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(ex.getMessage()));
    }
    
    /**
     * 處理領域異常
     *
     * @param ex 領域異常
     * @return 400 錯誤回應
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiResponse<Void>> handleDomainException(DomainException ex) {
        log.warn("Domain exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(ex.getMessage()));
    }
    
    /**
     * 處理參數驗證異常
     *
     * @param ex 方法參數驗證異常
     * @return 400 錯誤回應，包含詳細的欄位錯誤資訊
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        log.warn("Validation failed: {}", errors);

        ApiResponse<Map<String, String>> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage("請求參數驗證失敗");
        response.setData(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 處理非法參數異常
     *
     * @param ex 非法參數異常
     * @return 400 錯誤回應
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(ex.getMessage()));
    }
    
    /**
     * 處理運行時異常
     *
     * @param ex 運行時異常
     * @return 500 錯誤回應
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("系統發生錯誤"));
    }
    
    /**
     * 處理所有其他異常
     *
     * @param ex 通用異常
     * @return 500 錯誤回應
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("系統發生未預期的錯誤"));
    }
}
