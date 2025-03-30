package com.cinemareserve.cinemareserve.service;

import com.cinemareserve.cinemareserve.dto.user.UpdateUserDto;
import com.cinemareserve.cinemareserve.dto.user.UserDto;
import com.cinemareserve.cinemareserve.enitity.User;
import com.cinemareserve.cinemareserve.exception.*;
import com.cinemareserve.cinemareserve.mapper.UserMapper;
import com.cinemareserve.cinemareserve.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public Page<UserDto> findUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId"));
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(UserMapper::mapUserToUserDto);
    }

    public UserDto findUserById(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        return UserMapper.mapUserToUserDto(user);
    }

    public UserDto updateUser(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        if(updateUserDto.getFirstName() != null && !updateUserDto.getFirstName().isEmpty()){
            user.setFirstName(updateUserDto.getFirstName());
        }
        if(updateUserDto.getLastName() != null && !updateUserDto.getLastName().isEmpty()){
            user.setLastName(updateUserDto.getLastName());
        }
        if(updateUserDto.getEmail() != null && !updateUserDto.getEmail().isEmpty()){
            if(userRepository.findByEmailIgnoreCase(updateUserDto.getEmail()).isPresent()
                    && !user.getEmail().equalsIgnoreCase(updateUserDto.getEmail())){
                throw new EmailAlreadyExistsException("User with email " + updateUserDto.getEmail() + " already exists");
            }
            user.setEmail(updateUserDto.getEmail());
        }
        if(updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()){
            if (!Objects.equals(updateUserDto.getPassword(), updateUserDto.getConfirmPassword())){
                throw new PasswordsAreNotTheSameException("Passwords are not the same");
            } else {
                user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            }
        }
        if(updateUserDto.getRole() != null){
            user.setRole(updateUserDto.getRole());
        }

        User updatedUser = userRepository.save(user);
        return UserMapper.mapUserToUserDto(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        userRepository.delete(user);
    }

    public Page<UserDto> searchUsers(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId"));
        Page<User> usersPage = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, query, pageable);
        return usersPage.map(UserMapper::mapUserToUserDto);
    }
}
