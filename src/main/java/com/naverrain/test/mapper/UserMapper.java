package com.naverrain.test.mapper;

import com.naverrain.test.dtos.UserDto;
import com.naverrain.test.entities.User;

public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
