package com.nicenpc.adapterinbound.controller;

import com.nicenpc.application.UserService;
import com.nicenpc.domain.User;
import com.nicenpc.adapterinbound.dto.CreateUserRequest;
import com.nicenpc.adapterinbound.dto.UserResponse;
import com.nicenpc.adapterinbound.mapper.UserDTOMapper;
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
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> responses = users.stream()
                .map(UserDTOMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserResponse response = UserDTOMapper.INSTANCE.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = UserDTOMapper.INSTANCE.toDomain(request);
        User savedUser = userService.createUser(user.getName(), user.getEmail());
        UserResponse response = UserDTOMapper.INSTANCE.toResponse(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
