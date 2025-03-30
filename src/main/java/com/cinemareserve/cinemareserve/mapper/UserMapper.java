package com.cinemareserve.cinemareserve.mapper;

import com.cinemareserve.cinemareserve.dto.auth.RegisterUserDto;
import com.cinemareserve.cinemareserve.dto.user.UserDto;
import com.cinemareserve.cinemareserve.enitity.User;
import com.cinemareserve.cinemareserve.shared.Role;

import java.util.List;

public class UserMapper {
    public static UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static User mapRegisterUserDtoToUser(RegisterUserDto registerUserDto) {
        return new User(
                null,
                registerUserDto.getFirstName(),
                registerUserDto.getLastName(),
                registerUserDto.getEmail(),
                registerUserDto.getPassword(),
                registerUserDto.getRole() != null ? registerUserDto.getRole() : Role.USER,
                List.of()
        );
    }
}
