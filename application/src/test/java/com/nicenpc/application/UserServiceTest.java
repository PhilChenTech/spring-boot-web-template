package com.nicenpc.application;

import com.nicenpc.application.bus.CommandBus;
import com.nicenpc.application.bus.QueryBus;
import com.nicenpc.application.command.CreateUserCommand;
import com.nicenpc.application.command.DeleteAllUsersCommand;
import com.nicenpc.application.query.*;
import com.nicenpc.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * UserService應用服務測試
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private CommandBus commandBus;

    @Mock
    private QueryBus queryBus;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(commandBus, queryBus);
    }

    @Test
    void testGetAllUsers() {
        // Given
        User user1 = new User(1L, "John", "john@example.com");
        User user2 = new User(2L, "Jane", "jane@example.com");
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(queryBus.send(any(GetAllUsersQuery.class))).thenReturn(expectedUsers);

        // When
        List<User> actualUsers = userService.getAllUsers();

        // Then
        assertEquals(expectedUsers, actualUsers);
        verify(queryBus).send(any(GetAllUsersQuery.class));
    }

    @Test
    void testGetUserById() {
        // Given
        Long userId = 1L;
        User expectedUser = new User(userId, "John", "john@example.com");
        when(queryBus.send(any(GetUserByIdQuery.class))).thenReturn(expectedUser);

        // When
        User actualUser = userService.getUserById(userId);

        // Then
        assertEquals(expectedUser, actualUser);
        ArgumentCaptor<GetUserByIdQuery> captor = ArgumentCaptor.forClass(GetUserByIdQuery.class);
        verify(queryBus).send(captor.capture());
        assertEquals(userId, captor.getValue().getUserId());
    }

    @Test
    void testGetUserByEmail() {
        // Given
        String email = "john@example.com";
        User expectedUser = new User(1L, "John", email);
        when(queryBus.send(any(GetUserByEmailQuery.class))).thenReturn(expectedUser);

        // When
        User actualUser = userService.getUserByEmail(email);

        // Then
        assertEquals(expectedUser, actualUser);
        ArgumentCaptor<GetUserByEmailQuery> captor = ArgumentCaptor.forClass(GetUserByEmailQuery.class);
        verify(queryBus).send(captor.capture());
        assertEquals(email, captor.getValue().getEmail());
    }

    @Test
    void testCreateUser() {
        // Given
        String name = "John";
        String email = "john@example.com";
        User expectedUser = new User(1L, name, email);
        when(queryBus.send(any(GetUserByEmailQuery.class))).thenReturn(expectedUser);

        // When
        User actualUser = userService.createUser(name, email);

        // Then
        assertEquals(expectedUser, actualUser);
        ArgumentCaptor<CreateUserCommand> commandCaptor = ArgumentCaptor.forClass(CreateUserCommand.class);
        verify(commandBus).send(commandCaptor.capture());
        assertEquals(name, commandCaptor.getValue().getName());
        assertEquals(email, commandCaptor.getValue().getEmail());

        ArgumentCaptor<GetUserByEmailQuery> queryCaptor = ArgumentCaptor.forClass(GetUserByEmailQuery.class);
        verify(queryBus).send(queryCaptor.capture());
        assertEquals(email, queryCaptor.getValue().getEmail());
    }

    @Test
    void testCount() {
        // Given
        long expectedCount = 5L;
        when(queryBus.send(any(CountUsersQuery.class))).thenReturn(expectedCount);

        // When
        long actualCount = userService.count();

        // Then
        assertEquals(expectedCount, actualCount);
        verify(queryBus).send(any(CountUsersQuery.class));
    }

    @Test
    void testExistsByEmail() {
        // Given
        String email = "john@example.com";
        when(queryBus.send(any(ExistsByEmailQuery.class))).thenReturn(true);

        // When
        boolean exists = userService.existsByEmail(email);

        // Then
        assertTrue(exists);
        ArgumentCaptor<ExistsByEmailQuery> captor = ArgumentCaptor.forClass(ExistsByEmailQuery.class);
        verify(queryBus).send(captor.capture());
        assertEquals(email, captor.getValue().getEmail());
    }

    @Test
    void testDeleteAll() {
        // When
        userService.deleteAll();

        // Then
        verify(commandBus).send(any(DeleteAllUsersCommand.class));
    }

    @Test
    void testFindByEmailDomain() {
        // Given
        String domain = "example.com";
        User user1 = new User(1L, "John", "john@example.com");
        User user2 = new User(2L, "Jane", "jane@example.com");
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(queryBus.send(any(FindByEmailDomainQuery.class))).thenReturn(expectedUsers);

        // When
        List<User> actualUsers = userService.findByEmailDomain(domain);

        // Then
        assertEquals(expectedUsers, actualUsers);
        ArgumentCaptor<FindByEmailDomainQuery> captor = ArgumentCaptor.forClass(FindByEmailDomainQuery.class);
        verify(queryBus).send(captor.capture());
        assertEquals(domain, captor.getValue().getDomain());
    }
}
