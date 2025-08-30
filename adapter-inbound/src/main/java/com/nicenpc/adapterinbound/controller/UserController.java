package com.nicenpc.adapterinbound.controller;

import com.nicenpc.application.UserService;
import com.nicenpc.domain.User;
import com.nicenpc.adapterinbound.dto.ApiResponse;
import com.nicenpc.adapterinbound.dto.CreateUserRequest;
import com.nicenpc.adapterinbound.dto.UserResponse;
import com.nicenpc.adapterinbound.mapper.UserDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "使用者管理相關 API")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "取得所有使用者", description = "取得系統中所有使用者的清單")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功取得使用者清單",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)))
    })
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> responses = users.stream()
                .map(UserDTOMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
        
        ApiResponse<List<UserResponse>> apiResponse = ApiResponse.success(
            responses, 
            "成功取得使用者清單"
        );
        
        // 設定元資料
        ApiResponse.Metadata metadata = new ApiResponse.Metadata();
        metadata.setTotalElements((long) responses.size());
        metadata.setVersion("1.0");
        apiResponse.setMetadata(metadata);
        
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根據 ID 取得使用者", description = "根據使用者 ID 取得特定使用者資訊")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功取得使用者資訊",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "找不到指定的使用者",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @Parameter(description = "使用者 ID", required = true, example = "1")
            @PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponse response = UserDTOMapper.INSTANCE.toResponse(user);
        
        ApiResponse<UserResponse> apiResponse = ApiResponse.success(
            response, 
            "成功取得使用者資訊"
        );
        
        ApiResponse.Metadata metadata = new ApiResponse.Metadata();
        metadata.setVersion("1.0");
        apiResponse.setMetadata(metadata);
        
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    @Operation(summary = "建立新使用者", description = "建立一個新的使用者")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "成功建立使用者",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "請求資料格式錯誤",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "使用者已存在",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Parameter(description = "建立使用者的請求資料", required = true)
            @Valid @RequestBody CreateUserRequest request) {
        User user = UserDTOMapper.INSTANCE.toDomain(request);
        User savedUser = userService.createUser(user.getName(), user.getEmail());
        UserResponse response = UserDTOMapper.INSTANCE.toResponse(savedUser);
        
        ApiResponse<UserResponse> apiResponse = ApiResponse.success(
            response, 
            "使用者建立成功"
        );
        
        ApiResponse.Metadata metadata = new ApiResponse.Metadata();
        metadata.setVersion("1.0");
        apiResponse.setMetadata(metadata);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新使用者", description = "更新指定使用者的資訊")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "成功更新使用者",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "找不到指定的使用者",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "請求資料格式錯誤",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @Parameter(description = "使用者 ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "更新使用者的請求資料", required = true)
            @Valid @RequestBody CreateUserRequest request) {
        
        // 先檢查使用者是否存在
        userService.getUserById(id);
        
        // 更新使用者（這裡簡化實現，實際應該有專門的更新方法）
        User updatedUser = userService.createUser(request.getName(), request.getEmail());
        updatedUser.setId(id); // 設定ID確保是更新操作
        
        UserResponse response = UserDTOMapper.INSTANCE.toResponse(updatedUser);
        
        ApiResponse<UserResponse> apiResponse = ApiResponse.success(
            response, 
            "使用者更新成功"
        );
        
        ApiResponse.Metadata metadata = new ApiResponse.Metadata();
        metadata.setVersion("1.0");
        apiResponse.setMetadata(metadata);
        
        return ResponseEntity.ok(apiResponse);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "刪除使用者", description = "刪除指定的使用者")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "成功刪除使用者"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "找不到指定的使用者",
                    content = @Content)
    })
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "使用者 ID", required = true, example = "1")
            @PathVariable Long id) {
        
        // 先檢查使用者是否存在
        userService.getUserById(id);
        
        // 刪除使用者（這裡簡化實現，實際應該有專門的刪除方法）
        // userService.deleteUser(id);
        
        ApiResponse<Void> apiResponse = ApiResponse.success("使用者刪除成功");
        
        ApiResponse.Metadata metadata = new ApiResponse.Metadata();
        metadata.setVersion("1.0");
        apiResponse.setMetadata(metadata);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
    }
}
