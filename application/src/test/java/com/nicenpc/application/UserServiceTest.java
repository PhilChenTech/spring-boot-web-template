package com.nicenpc.application;

import com.nicenpc.domain.User;
import com.nicenpc.domain.exception.UserValidationException;
import com.nicenpc.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * UserService應用服務測試
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void testGetAllUsers() {
        // Given
        User user1 = new User(1L, "John", "john@example.com");
        User user2 = new User(2L, "Jane", "jane@example.com");
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // When
        List<User> actualUsers = userService.getAllUsers();

        // Then
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById() {
        // Given
        Long userId = 1L;
        User expectedUser = new User(userId, "John", "john@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // When
        User actualUser = userService.getUserById(userId);

        // Then
        assertEquals(expectedUser, actualUser);
        verify(userRepository).findById(userId);
    }

    @Test
    void testGetUserByIdNotFound() {
        // Given
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        User actualUser = userService.getUserById(userId);

        // Then
        assertNull(actualUser);
        verify(userRepository).findById(userId);
    }

    @Test
    void testGetUserByEmail() {
        // Given
        String email = "john@example.com";
        User expectedUser = new User(1L, "John", email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        // When
        User actualUser = userService.getUserByEmail(email);

        // Then
        assertEquals(expectedUser, actualUser);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void testCreateUserSuccess() {
        // Given
        String name = "John";
        String email = "john@example.com";
        User expectedUser = new User(1L, name, email);
        
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // When
        User actualUser = userService.createUser(name, email);

        // Then
        assertEquals(expectedUser, actualUser);
        verify(userRepository).existsByEmail(email);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUserWithInvalidName() {
        // Given
        String invalidName = "";
        String email = "john@example.com";

        // When & Then
        assertThrows(UserValidationException.class, 
            () -> userService.createUser(invalidName, email));
        
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUserWithInvalidEmail() {
        // Given
        String name = "John";
        String invalidEmail = "invalid-email";

        // When & Then
        assertThrows(UserValidationException.class, 
            () -> userService.createUser(name, invalidEmail));
        
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUserWithExistingEmail() {
        // Given
        String name = "John";
        String email = "john@example.com";
        
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userService.createUser(name, email)
        );
        
        assertTrue(exception.getMessage().contains("電子郵件已存在"));
        verify(userRepository).existsByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCount() {
        // Given
        long expectedCount = 5L;
        when(userRepository.count()).thenReturn(expectedCount);

        // When
        long actualCount = userService.count();

        // Then
        assertEquals(expectedCount, actualCount);
        verify(userRepository).count();
    }

    @Test
    void testExistsByEmail() {
        // Given
        String email = "john@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // When
        boolean exists = userService.existsByEmail(email);

        // Then
        assertTrue(exists);
        verify(userRepository).existsByEmail(email);
    }

    @Test
    void testDeleteAll() {
        // When
        userService.deleteAll();

        // Then
        verify(userRepository).deleteAll();
    }

    @Test
    void testFindByEmailDomain() {
        // Given
        String domain = "example.com";
        User user1 = new User(1L, "John", "john@example.com");
        User user2 = new User(2L, "Jane", "jane@example.com");
        List<User> expectedUsers = Arrays.asList(user1, user2);
        
        when(userRepository.findByEmailDomain(domain)).thenReturn(expectedUsers);

        // When
        List<User> actualUsers = userService.findByEmailDomain(domain);

        // Then
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findByEmailDomain(domain);
    }
}