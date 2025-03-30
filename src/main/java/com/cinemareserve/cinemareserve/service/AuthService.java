package com.cinemareserve.cinemareserve.service;

import com.cinemareserve.cinemareserve.dto.auth.LoginDto;
import com.cinemareserve.cinemareserve.dto.auth.RegisterUserDto;
import com.cinemareserve.cinemareserve.dto.user.UserDto;
import com.cinemareserve.cinemareserve.enitity.User;
import com.cinemareserve.cinemareserve.exception.EmailAlreadyExistsException;
import com.cinemareserve.cinemareserve.exception.InvalidCredentialsException;
import com.cinemareserve.cinemareserve.exception.PasswordsAreNotTheSameException;
import com.cinemareserve.cinemareserve.mapper.UserMapper;
import com.cinemareserve.cinemareserve.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserDto register(RegisterUserDto registerUserDto) {
        if(userRepository.findByEmailIgnoreCase(registerUserDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("User with email " + registerUserDto.getEmail() + " already exists");
        }

        if (!Objects.equals(registerUserDto.getPassword(), registerUserDto.getConfirmPassword())){
            throw new PasswordsAreNotTheSameException("Passwords are not the same");
        }

        User user = UserMapper.mapRegisterUserDtoToUser(registerUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User addedUser = userRepository.save(user);
        return UserMapper.mapUserToUserDto(addedUser);
    }

    public UserDto login(LoginDto loginDto) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(loginDto.getEmail());
        if (user.isEmpty()) {
            throw new InvalidCredentialsException("User with email " + loginDto.getEmail() + " not found");
        }
        if(!passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        return UserMapper.mapUserToUserDto(user.get());
    }
}
