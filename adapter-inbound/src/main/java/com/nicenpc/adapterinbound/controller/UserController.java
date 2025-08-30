package com.nicenpc.adapterinbound.controller;

import com.nicenpc.application.UserService;
import com.nicenpc.domain.User;
import com.nicenpc.adapterinbound.dto.CreateUserRequest;
import com.nicenpc.adapterinbound.dto.UserResponse;
import com.nicenpc.adapterinbound.mapper.UserDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "使用者管理相關 API")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "取得所有使用者", description = "取得系統中所有使用者的清單")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功取得使用者清單",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> responses = users.stream()
                .map(UserDTOMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根據 ID 取得使用者", description = "根據使用者 ID 取得特定使用者資訊")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功取得使用者資訊",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "找不到指定的使用者",
                    content = @Content)
    })
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "使用者 ID", required = true, example = "1")
            @PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserResponse response = UserDTOMapper.INSTANCE.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "建立新使用者", description = "建立一個新的使用者")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "成功建立使用者",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "請求資料格式錯誤",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "使用者已存在",
                    content = @Content)
    })
    public ResponseEntity<UserResponse> createUser(
            @Parameter(description = "建立使用者的請求資料", required = true)
            @Valid @RequestBody CreateUserRequest request) {
        User user = UserDTOMapper.INSTANCE.toDomain(request);
        User savedUser = userService.createUser(user.getName(), user.getEmail());
        UserResponse response = UserDTOMapper.INSTANCE.toResponse(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
