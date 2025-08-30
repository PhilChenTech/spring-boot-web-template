package com.nicenpc.adapterinbound.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicenpc.application.UserService;
import com.nicenpc.domain.User;
import com.nicenpc.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserController 控制器測試
 * 使用 @WebMvcTest 進行 Controller 層的單元測試
 * 完全不依賴資料庫，純粹使用 Mock 物件
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsersEmpty() throws Exception {
        // Given
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.length()").value(0))
                .andExpect(jsonPath("$.success").value(true));
    }
    
    @Test
    void testGetAllUsersWithData() throws Exception {
        // Given
        User user1 = new User(1L, "John", "john@example.com");
        User user2 = new User(2L, "Jane", "jane@example.com");
        List<User> users = List.of(user1, user2);
        when(userService.getAllUsers()).thenReturn(users);

        // When & Then
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("John"))
                .andExpect(jsonPath("$.data[0].email").value("john@example.com"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("Jane"))
                .andExpect(jsonPath("$.data[1].email").value("jane@example.com"))
                .andExpect(jsonPath("$.success").value(true));
    }
    
    @Test
    void testGetUserByIdExists() throws Exception {
        // Given
        User user = new User(1L, "John", "john@example.com");
        when(userService.getUserById(1L)).thenReturn(user);

        // When & Then
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"))
                .andExpect(jsonPath("$.success").value(true));
    }
    
    @Test
    void testGetUserByIdNotExists() throws Exception {
        // Given
        when(userService.getUserById(999L)).thenThrow(new UserNotFoundException(999L));

        // When & Then
        mockMvc.perform(get("/api/v1/users/999"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testCreateUserValid() throws Exception {
        // Given
        User savedUser = new User(1L, "John", "john@example.com");
        when(userService.createUser(anyString(), anyString())).thenReturn(savedUser);

        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"email\":\"john@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"))
                .andExpect(jsonPath("$.success").value(true));
    }
    
    @Test
    void testCreateUserInvalidRequest() throws Exception {
        // When & Then - 缺少必要欄位
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateUserInvalidEmail() throws Exception {
        // When & Then - 無效的電子郵件格式
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"email\":\"invalid-email\"}"))
                .andExpect(status().isBadRequest());
    }
}