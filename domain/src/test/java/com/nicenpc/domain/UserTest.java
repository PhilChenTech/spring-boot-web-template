package com.nicenpc.domain;

import com.nicenpc.domain.exception.UserValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * User領域實體測試
 */
class UserTest {

    @Test
    void testUserCreation() {
        User user = new User(1L, "John Doe", "john@example.com");
        
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void testUserFactoryCreate() {
        User user = User.create("John Doe", "john@example.com");

        assertNull(user.getId()); // ID is not set by factory
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void testUserFactoryCreateInvalidEmail() {
        assertThrows(UserValidationException.class, () -> {
            User.create("John Doe", "invalid-email");
        });
    }

    @Test
    void testValidEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        
        assertTrue(user.isValidEmail());
    }

    @Test
    void testValidEmailWithSubdomain() {
        User user = new User();
        user.setEmail("test@sub.example.com");
        
        assertTrue(user.isValidEmail());
    }

    @Test
    void testInvalidEmailNull() {
        User user = new User();
        user.setEmail(null);
        
        assertFalse(user.isValidEmail());
    }

    @Test
    void testInvalidEmailNoAtSymbol() {
        User user = new User();
        user.setEmail("testexample.com");
        
        assertFalse(user.isValidEmail());
    }

    @Test
    void testInvalidEmailNoDomain() {
        User user = new User();
        user.setEmail("test@");
        
        assertFalse(user.isValidEmail());
    }

    @Test
    void testCanReceiveNotifications() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setActive(true);

        assertTrue(user.canReceiveNotifications());
    }

    @Test
    void testCannotReceiveNotificationsWhenInactive() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setActive(false);

        assertFalse(user.canReceiveNotifications());
    }

    @Test
    void testCannotReceiveNotificationsWithInvalidEmail() {
        User user = new User();
        user.setEmail("invalid-email");
        user.setActive(true);

        assertFalse(user.canReceiveNotifications());
    }

    @Test
    void testValidName() {
        User user = new User();
        user.setName("John Doe");
        
        assertTrue(user.isValidName());
    }

    @Test
    void testInvalidNameNull() {
        User user = new User();
        user.setName(null);
        
        assertFalse(user.isValidName());
    }

    @Test
    void testInvalidNameEmpty() {
        User user = new User();
        user.setName("");
        
        assertFalse(user.isValidName());
    }

    @Test
    void testInvalidNameWhitespace() {
        User user = new User();
        user.setName("   ");
        
        assertFalse(user.isValidName());
    }

    @Test
    void testInvalidNameTooLong() {
        User user = new User();
        user.setName("a".repeat(101));
        
        assertFalse(user.isValidName());
    }

    @Test
    void testValidateSuccess() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        
        assertDoesNotThrow(() -> user.validate());
    }

    @Test
    void testValidateFailsWithInvalidName() {
        User user = new User();
        user.setName("");
        user.setEmail("john@example.com");
        
        UserValidationException exception = assertThrows(
            UserValidationException.class,
            () -> user.validate()
        );
        assertTrue(exception.getMessage().contains("使用者名稱"));
    }

    @Test
    void testValidateFailsWithInvalidEmail() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("invalid-email");
        
        UserValidationException exception = assertThrows(
            UserValidationException.class,
            () -> user.validate()
        );
        assertTrue(exception.getMessage().contains("電子郵件"));
    }

    @Test
    void testEquals() {
        User user1 = new User(1L, "John", "john@example.com");
        User user2 = new User(1L, "John", "john@example.com");
        User user3 = new User(2L, "Jane", "jane@example.com");
        
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertNotEquals(user1, null);
        assertNotEquals(user1, "string");
    }

    @Test
    void testHashCode() {
        User user1 = new User(1L, "John", "john@example.com");
        User user2 = new User(1L, "John", "john@example.com");
        
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToString() {
        User user = new User(1L, "John", "john@example.com");
        String result = user.toString();
        
        assertTrue(result.contains("1"));
        assertTrue(result.contains("John"));
        assertTrue(result.contains("john@example.com"));
    }

    @Test
    void testSetId() {
        // Given
        User user = new User();
        Long newId = 123L;
        
        // When
        user.setId(newId);
        
        // Then
        assertEquals(newId, user.getId());
    }

    @Test
    void testSetIdWithNull() {
        // Given
        User user = new User(1L, "John", "john@example.com");
        
        // When
        user.setId(null);
        
        // Then
        assertNull(user.getId());
    }
}
