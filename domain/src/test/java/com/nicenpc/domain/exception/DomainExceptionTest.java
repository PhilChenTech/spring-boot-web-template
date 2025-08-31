package com.nicenpc.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DomainException 測試類
 *
 * <p>測試領域異常基類的功能，通過具體的子類進行測試。
 * 由於DomainException是抽象類，使用UserValidationException作為測試實例。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
class DomainExceptionTest {

    @Test
    void testExceptionCreationWithMessage() {
        // Given
        String message = "Domain rule violation";
        
        // When
        UserValidationException exception = new UserValidationException(message);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof DomainException);
    }

    @Test
    void testExceptionCreationWithMessageAndCause() {
        // Given
        String message = "Domain error occurred";
        Throwable cause = new IllegalStateException("Invalid state");
        
        // When
        UserValidationException exception = new UserValidationException(message, cause);

        // Then
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof DomainException);
    }

    @Test
    void testExceptionMessage() {
        // Given
        String expectedMessage = "Test domain exception";

        // When
        UserValidationException exception = new UserValidationException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testExceptionMessageNotNull() {
        // Given
        String message = "Non-null message";

        // When
        UserValidationException exception = new UserValidationException(message);

        // Then
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    void testExceptionCause() {
        // Given
        String message = "Exception with cause";
        RuntimeException cause = new RuntimeException("Root cause");

        // When
        UserValidationException exception = new UserValidationException(message, cause);

        // Then
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testExceptionToString() {
        // Given
        String message = "Test exception";

        // When
        UserValidationException exception = new UserValidationException(message);

        // Then
        String result = exception.toString();
        assertNotNull(result);
        assertTrue(result.contains("UserValidationException"));
        assertTrue(result.contains(message));
    }
}
