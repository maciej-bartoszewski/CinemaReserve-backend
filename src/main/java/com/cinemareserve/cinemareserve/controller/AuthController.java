package com.cinemareserve.cinemareserve.controller;

import com.cinemareserve.cinemareserve.dto.auth.LoginDto;
import com.cinemareserve.cinemareserve.dto.auth.RegisterUserDto;
import com.cinemareserve.cinemareserve.dto.user.UserDto;
import com.cinemareserve.cinemareserve.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthService authService;

    // Create a new user
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        UserDto addedUser = authService.register(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto) {
        UserDto userDto = authService.login(loginDto);
        return ResponseEntity.ok(userDto);
    }
}
