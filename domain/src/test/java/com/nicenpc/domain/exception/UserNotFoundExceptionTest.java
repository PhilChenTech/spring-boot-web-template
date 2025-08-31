package com.nicenpc.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserNotFoundException 單元測試
 * 純領域邏輯測試，不依賴 Spring 或外部系統
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
        assertTrue(exception.getMessage().contains(userId.toString()));
        assertTrue(exception.getMessage().contains("不存在"));
    }

    @Test
    void testExceptionCreationWithMessage() {
        // Given
        String email = "notfound@example.com";
        
        // When
        UserNotFoundException exception = new UserNotFoundException(email);
        
        // Then
        assertNotNull(exception);
        String expectedMessage = "使用者不存在: Email = " + email;
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getUserId()); // 使用字串建構函式時 userId 應為 null
    }

    @Test
    void testExceptionMessageWithId() {
        // Given
        Long userId = 456L;
        
        // When
        UserNotFoundException exception = new UserNotFoundException(userId);
        
        // Then
        String expectedMessage = "使用者不存在: ID = " + userId;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testExceptionWithNullId() {
        // Given
        Long userId = null;
        
        // When
        UserNotFoundException exception = new UserNotFoundException(userId);
        
        // Then
        assertNotNull(exception);
        assertNull(exception.getUserId());
        assertTrue(exception.getMessage().contains("null"));
    }

    @Test
    void testExceptionWithNullEmail() {
        // Given
        String email = null;
        
        // When
        UserNotFoundException exception = new UserNotFoundException(email);
        
        // Then
        assertNotNull(exception);
        String expectedMessage = "使用者不存在: Email = null";
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getUserId());
    }

    @Test
    void testExceptionInheritance() {
        // Given
        Long userId = 789L;
        
        // When
        UserNotFoundException exception = new UserNotFoundException(userId);
        
        // Then
        assertTrue(exception instanceof DomainException);
        assertTrue(exception instanceof RuntimeException);
    }
}
