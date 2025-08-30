package com.nicenpc.bootstrap.integration;

import com.nicenpc.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 簡單的單元測試
 * 完全不依賴 Spring 和任何外部系統
 */
class UserWebTest {

    @Test
    void testUserCreation() {
        // Given
        String name = "Test User";
        String email = "test@example.com";

        // When
        User user = User.create(name, email);

        // Then
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.isValidEmail()).isTrue();
        assertThat(user.isValidName()).isTrue();
    }

    @Test
    void testUserValidation() {
        // Test valid user
        User validUser = new User(1L, "Valid User", "valid@example.com");
        assertThat(validUser.isValidEmail()).isTrue();
        assertThat(validUser.isValidName()).isTrue();

        // Test invalid email
        User invalidEmailUser = new User(2L, "User", "invalid-email");
        assertThat(invalidEmailUser.isValidEmail()).isFalse();

        // Test invalid name
        User invalidNameUser = new User(3L, "", "valid@example.com");
        assertThat(invalidNameUser.isValidName()).isFalse();
    }

    @Test
    void testUserBusinessRules() {
        // Given
        User activeUser = new User(1L, "Active User", "active@example.com");

        // Then - 測試業務規則
        assertThat(activeUser.isActive()).isTrue();
        assertThat(activeUser.canReceiveNotifications()).isTrue();
    }
}
