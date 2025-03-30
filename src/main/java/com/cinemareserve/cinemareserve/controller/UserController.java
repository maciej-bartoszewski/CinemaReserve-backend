package com.cinemareserve.cinemareserve.controller;

import com.cinemareserve.cinemareserve.dto.user.UpdateUserDto;
import com.cinemareserve.cinemareserve.dto.user.UserDto;
import com.cinemareserve.cinemareserve.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    UserService userService;

    // Find all users
    @GetMapping
    public ResponseEntity<Page<UserDto>> findUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDto> usersPage = userService.findUsers(page, size);
        return ResponseEntity.ok(usersPage);
    }

    // Find users by first name, last name, or email
    @GetMapping("/search")
    public ResponseEntity<Page<UserDto>> searchUsers(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDto> usersPage = userService.searchUsers(query, page, size);
        return ResponseEntity.ok(usersPage);
    }

    // Find a user by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long userId) {
        UserDto userDto = userService.findUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    // Update a user by id
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUser = userService.updateUser(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user by id
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
