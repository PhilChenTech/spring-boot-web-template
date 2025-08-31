package com.nicenpc.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DomainException 純單元測試
 * 測試領域異常基類的創建和繼承關係
 */
class DomainExceptionTest {

    @Test
    void testExceptionCreationWithMessage() {
        // Given
        String message = "Domain rule violation";
        
        // When
        DomainException exception = new DomainException(message);
        
        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionCreationWithMessageAndCause() {
        // Given
        String message = "Domain error occurred";
        Throwable cause = new IllegalStateException("Invalid state");
        
        // When
        DomainException exception = new DomainException(message, cause);
        
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
        DomainException exception = new DomainException(message);
        
        // Then
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testExceptionWithEmptyMessage() {
        // Given
        String message = "";
        
        // When
        DomainException exception = new DomainException(message);
        
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
        DomainException exception = new DomainException(message, cause);
        
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
        DomainException exception = new DomainException(message);
        
        // Then
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }
}
