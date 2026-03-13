package com.library.libraryapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.library.libraryapi.dto.ApiResponse;
import com.library.libraryapi.dto.UserDto;
import com.library.libraryapi.dto.DtoMapper;
import com.library.libraryapi.models.User;
import com.library.libraryapi.services.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DtoMapper dtoMapper;

    // Obtener todos los usuarios (con DTOs para evitar referencias circulares)
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = dtoMapper.toUserDtoList(users);
        return ResponseEntity.ok(new ApiResponse<>("Users retrieved successfully", userDtos));
    }

    // Crear usuario
    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        UserDto userDto = dtoMapper.toUserDto(newUser);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>("User created successfully", userDto));
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = dtoMapper.toUserDto(user);
            return ResponseEntity.ok(new ApiResponse<>("User retrieved successfully", userDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            UserDto userDto = dtoMapper.toUserDto(updatedUser);
            return ResponseEntity.ok(new ApiResponse<>("User updated successfully", userDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse<>("User deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage(), null));
        }
    }
}
