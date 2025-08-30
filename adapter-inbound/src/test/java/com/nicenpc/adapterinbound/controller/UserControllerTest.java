package com.nicenpc.adapterinbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicenpc.adapterinbound.dto.UserDTO;
import com.nicenpc.adapterinbound.mapper.UserDTOMapper;
import com.nicenpc.application.UserService;
import com.nicenpc.domain.User;
import com.nicenpc.domain.exception.UserValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserController控制器測試
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDTOMapper userDTOMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        // Given
        User user1 = new User(1L, "John", "john@example.com");
        User user2 = new User(2L, "Jane", "jane@example.com");
        List<User> users = Arrays.asList(user1, user2);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId(1L);
        userDTO1.setName("John");
        userDTO1.setEmail("john@example.com");

        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setName("Jane");
        userDTO2.setEmail("jane@example.com");

        List<UserDTO> userDTOs = Arrays.asList(userDTO1, userDTO2);

        when(userService.getAllUsers()).thenReturn(users);
        when(userDTOMapper.toDTO(user1)).thenReturn(userDTO1);
        when(userDTOMapper.toDTO(user2)).thenReturn(userDTO2);

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpected(jsonPath("$.length()").value(2))
                .andExpected(jsonPath("$[0].id").value(1))
                .andExpected(jsonPath("$[0].name").value("John"))
                .andExpected(jsonPath("$[0].email").value("john@example.com"))
                .andExpected(jsonPath("$[1].id").value(2))
                .andExpected(jsonPath("$[1].name").value("Jane"))
                .andExpected(jsonPath("$[1].email").value("jane@example.com"));
    }

    @Test
    void testGetAllUsersEmpty() throws Exception {
        // Given
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpected(status().isOk())
                .andExpected(content().contentType(MediaType.APPLICATION_JSON))
                .andExpected(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetUserById() throws Exception {
        // Given
        Long userId = 1L;
        User user = new User(userId, "John", "john@example.com");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setName("John");
        userDTO.setEmail("john@example.com");

        when(userService.getUserById(userId)).thenReturn(user);
        when(userDTOMapper.toDTO(user)).thenReturn(userDTO);

        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpected(status().isOk())
                .andExpected(content().contentType(MediaType.APPLICATION_JSON))
                .andExpected(jsonPath("$.id").value(userId))
                .andExpected(jsonPath("$.name").value("John"))
                .andExpected(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        // Given
        Long userId = 999L;
        when(userService.getUserById(userId)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpected(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        // Given
        UserDTO createUserDTO = new UserDTO();
        createUserDTO.setName("John");
        createUserDTO.setEmail("john@example.com");

        User createdUser = new User(1L, "John", "john@example.com");
        UserDTO responseDTO = new UserDTO();
        responseDTO.setId(1L);
        responseDTO.setName("John");
        responseDTO.setEmail("john@example.com");

        when(userService.createUser(anyString(), anyString())).thenReturn(createdUser);
        when(userDTOMapper.toDTO(createdUser)).thenReturn(responseDTO);

        // When & Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpected(status().isCreated())
                .andExpected(content().contentType(MediaType.APPLICATION_JSON))
                .andExpected(jsonPath("$.id").value(1))
                .andExpected(jsonPath("$.name").value("John"))
                .andExpected(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testCreateUserWithValidationError() throws Exception {
        // Given
        UserDTO createUserDTO = new UserDTO();
        createUserDTO.setName("");
        createUserDTO.setEmail("john@example.com");

        when(userService.createUser(anyString(), anyString()))
                .thenThrow(new UserValidationException("使用者名稱不能為空且長度不得超過100字元"));

        // When & Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpected(status().isBadRequest());
    }

    @Test
    void testCreateUserWithDuplicateEmail() throws Exception {
        // Given
        UserDTO createUserDTO = new UserDTO();
        createUserDTO.setName("John");
        createUserDTO.setEmail("john@example.com");

        when(userService.createUser(anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("電子郵件已存在: john@example.com"));

        // When & Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpected(status().isBadRequest());
    }

    @Test
    void testGetUserCount() throws Exception {
        // Given
        when(userService.count()).thenReturn(10L);

        // When & Then
        mockMvc.perform(get("/api/users/count"))
                .andExpected(status().isOk())
                .andExpected(content().contentType(MediaType.APPLICATION_JSON))
                .andExpected(jsonPath("$").value(10));
    }

    @Test
    void testFindUsersByEmailDomain() throws Exception {
        // Given
        String domain = "example.com";
        User user1 = new User(1L, "John", "john@example.com");
        List<User> users = Arrays.asList(user1);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId(1L);
        userDTO1.setName("John");
        userDTO1.setEmail("john@example.com");

        when(userService.findByEmailDomain(domain)).thenReturn(users);
        when(userDTOMapper.toDTO(user1)).thenReturn(userDTO1);

        // When & Then
        mockMvc.perform(get("/api/users/domain/{domain}", domain))
                .andExpected(status().isOk())
                .andExpected(content().contentType(MediaType.APPLICATION_JSON))
                .andExpected(jsonPath("$.length()").value(1))
                .andExpected(jsonPath("$[0].id").value(1))
                .andExpected(jsonPath("$[0].name").value("John"))
                .andExpected(jsonPath("$[0].email").value("john@example.com"));
    }
}