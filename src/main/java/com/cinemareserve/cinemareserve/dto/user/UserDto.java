package com.cinemareserve.cinemareserve.dto.user;

import com.cinemareserve.cinemareserve.shared.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
