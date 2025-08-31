package com.nicenpc.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserValidationException 純單元測試
 * 測試使用者驗證異常的創建和繼承關係
 */
class UserValidationExceptionTest {

    @Test
    void testExceptionCreationWithMessage() {
        // Given
        String message = "Invalid user data";
        
        // When
        UserValidationException exception = new UserValidationException(message);
        
        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionCreationWithMessageAndCause() {
        // Given
        String message = "Validation failed";
        Throwable cause = new IllegalArgumentException("Invalid argument");
        
        // When
        UserValidationException exception = new UserValidationException(message, cause);
        
        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testExceptionWithNullMessage() {
        // Given
        String message = null;
        
        // When
        UserValidationException exception = new UserValidationException(message);
        
        // Then
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testExceptionWithEmptyMessage() {
        // Given
        String message = "";
        
        // When
        UserValidationException exception = new UserValidationException(message);
        
        // Then
        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }

    @Test
    void testExceptionWithNullCause() {
        // Given
        String message = "Test message";
        Throwable cause = null;
        
        // When
        UserValidationException exception = new UserValidationException(message, cause);
        
        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testExceptionInheritance() {
        // Given
        String message = "Test message";
        
        // When
        UserValidationException exception = new UserValidationException(message);
        
        // Then
        assertTrue(exception instanceof DomainException);
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }
}
