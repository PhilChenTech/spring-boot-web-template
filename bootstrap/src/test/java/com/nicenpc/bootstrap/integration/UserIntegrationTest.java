package com.nicenpc.bootstrap.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicenpc.adapterinbound.dto.CreateUserRequest;
import com.nicenpc.adapterinbound.dto.UserResponse;
import com.nicenpc.adapterinbound.controller.UserController;
import com.nicenpc.adapterinbound.mapper.UserDTOMapper;
import com.nicenpc.application.UserService;
import com.nicenpc.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 使用者 Web 層測試
 * 使用 Mock 物件，不依賴任何資料庫
 */
@WebMvcTest(UserController.class)
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDTOMapper userDTOMapper;

    @BeforeEach
    void setUp() {
        // 設定 Mock 行為
        reset(userService, userDTOMapper);
    }

    @Test
    void testCompleteUserWorkflow() throws Exception {
        // Test 1: 創建使用者
        CreateUserRequest createRequest = CreateUserRequest.builder()
                .name("Integration Test User")
                .email("integration@test.com")
                .build();

        String createJson = objectMapper.writeValueAsString(createRequest);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Integration Test User"))
                .andExpect(jsonPath("$.email").value("integration@test.com"))
                .andExpect(jsonPath("$.id").exists());

        // Test 2: 查詢所有使用者
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Integration Test User"))
                .andExpect(jsonPath("$[0].email").value("integration@test.com"));

        // Test 3: 通過服務層直接測試
        User foundUser = userService.getUserByEmail("integration@test.com");
        assert foundUser != null;
        assert foundUser.getName().equals("Integration Test User");
        assert foundUser.isActive();

        // Test 4: 查詢單個使用者
        mockMvc.perform(get("/api/users/" + foundUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(foundUser.getId()))
                .andExpect(jsonPath("$.name").value("Integration Test User"))
                .andExpect(jsonPath("$.email").value("integration@test.com"));

        // Test 5: 測試業務邏輯
        assert userService.existsByEmail("integration@test.com");
        assert userService.count() == 1;
        assert foundUser.canReceiveNotifications(); // 測試 Domain 業務規則
    }

    @Test
    void testCreateDuplicateEmailShouldFail() throws Exception {
        // 創建第一個使用者
        CreateUserRequest request1 = CreateUserRequest.builder()
                .name("User One")
                .email("duplicate@test.com")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated());

        // 嘗試創建具有相同電子郵件的第二個使用者（應該失敗）
        CreateUserRequest request2 = CreateUserRequest.builder()
                .name("User Two")
                .email("duplicate@test.com")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testValidationConstraints() throws Exception {
        // 測試空名稱
        CreateUserRequest invalidName = CreateUserRequest.builder()
                .name("")
                .email("valid@test.com")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidName)))
                .andExpect(status().isBadRequest());

        // 測試無效電子郵件
        CreateUserRequest invalidEmail = CreateUserRequest.builder()
                .name("Valid Name")
                .email("invalid-email")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmail)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEmailDomainSearch() throws Exception {
        // 創建測試數據
        userService.createUser("User1", "user1@example.com");
        userService.createUser("User2", "user2@example.com");
        userService.createUser("User3", "user3@different.com");

        // 通過服務層測試 emailDomain 搜尋
        var exampleUsers = userService.findByEmailDomain("example.com");
        assert exampleUsers.size() == 2;
        assert exampleUsers.stream().allMatch(u -> u.getEmail().endsWith("@example.com"));

        var differentUsers = userService.findByEmailDomain("different.com");
        assert differentUsers.size() == 1;
        assert differentUsers.get(0).getEmail().equals("user3@different.com");
    }
}