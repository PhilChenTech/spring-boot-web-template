package com.nicenpc.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserNotFoundException 單元測試
 *
 * <p>測試使用者不存在異常的各種情況，包括ID和Email兩種建構方式。
 * 純領域邏輯測試，不依賴 Spring 或外部系統。</p>
 *
 * @author Nice NPC Team
 * @version 1.0
 * @since 1.0
 */
class UserNotFoundExceptionTest {

    @Test
    void testExceptionCreationWithId() {
        // Given
        Long userId = 123L;
        
        // When
        UserNotFoundException exception = new UserNotFoundException(userId);
        
        // Then
        assertNotNull(exception);
        assertEquals(userId, exception.getUserId());
        assertNull(exception.getEmail());
        assertTrue(exception.getMessage().contains(userId.toString()));
        assertTrue(exception.getMessage().contains("找不到使用者"));
    }

    @Test
    void testExceptionCreationWithEmail() {
        // Given
        String email = "notfound@example.com";
        
        // When
        UserNotFoundException exception = new UserNotFoundException(email);
        
        // Then
        assertNotNull(exception);
        assertEquals(email, exception.getEmail());
        assertNull(exception.getUserId());
        String expectedMessage = "找不到使用者: Email = " + email;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testExceptionMessageWithId() {
        // Given
        Long userId = 456L;
        
        // When
        UserNotFoundException exception = new UserNotFoundException(userId);
        
        // Then
        String expectedMessage = "找不到使用者: ID = " + userId;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testExceptionWithCustomMessage() {
        // Given
        String customMessage = "Custom not found message";
        Long userId = 789L;

        // When
        UserNotFoundException exception = new UserNotFoundException(customMessage, userId);

        // Then
        assertNotNull(exception);
        assertEquals(customMessage, exception.getMessage());
        assertEquals(userId, exception.getUserId());
        assertNull(exception.getEmail());
    }

    @Test
    void testExceptionIsInstanceOfDomainException() {
        // Given
        Long userId = 999L;

        // When
        UserNotFoundException exception = new UserNotFoundException(userId);

        // Then
        assertTrue(exception instanceof DomainException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionWithEmptyEmail() {
        // Given
        String email = "";

        // When
        UserNotFoundException exception = new UserNotFoundException(email);

        // Then
        assertNotNull(exception);
        assertEquals(email, exception.getEmail());
        assertTrue(exception.getMessage().contains("Email = "));
    }
}
