package com.naverrain.test.service;

import com.naverrain.test.dtos.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerUser(UserDto userDto);
    UserDto authenticateUser(String username, String password);
    List<UserDto> getAllUsers();
    void deleteUser(Long id);
    UserDto updateUser(Long id, UserDto userDto);


}
