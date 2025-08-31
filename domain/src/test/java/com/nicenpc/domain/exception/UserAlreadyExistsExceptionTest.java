package com.nicenpc.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserAlreadyExistsException 單元測試
 * 純領域邏輯測試，不依賴 Spring 或外部系統
 */
class UserAlreadyExistsExceptionTest {

    @Test
    void testExceptionCreation() {
        // Given
        String email = "test@example.com";
        
        // When
        UserAlreadyExistsException exception = new UserAlreadyExistsException(email);
        
        // Then
        assertNotNull(exception);
        assertEquals(email, exception.getEmail());
        assertTrue(exception.getMessage().contains(email));
        assertTrue(exception.getMessage().contains("已存在"));
    }

    @Test
    void testExceptionMessage() {
        // Given
        String email = "duplicate@example.com";
        
        // When
        UserAlreadyExistsException exception = new UserAlreadyExistsException(email);
        
        // Then
        String expectedMessage = "使用者已存在: " + email;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testExceptionWithNullEmail() {
        // Given
        String email = null;
        
        // When
        UserAlreadyExistsException exception = new UserAlreadyExistsException(email);
        
        // Then
        assertNotNull(exception);
        assertNull(exception.getEmail());
        assertTrue(exception.getMessage().contains("null"));
    }

    @Test
    void testExceptionWithEmptyEmail() {
        // Given
        String email = "";
        
        // When
        UserAlreadyExistsException exception = new UserAlreadyExistsException(email);
        
        // Then
        assertNotNull(exception);
        assertEquals("", exception.getEmail());
        assertTrue(exception.getMessage().contains("已存在"));
    }
}
