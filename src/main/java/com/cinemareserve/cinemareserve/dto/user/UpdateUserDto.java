package com.cinemareserve.cinemareserve.dto.user;

import com.cinemareserve.cinemareserve.shared.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    @Email(message = "Invalid email")
    private String email;
    private String password;
    private String confirmPassword;
    private Role role;
}
