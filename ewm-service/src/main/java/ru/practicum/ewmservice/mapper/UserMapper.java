package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.dto.NewUserRequest;
import ru.practicum.ewmservice.dto.UserDto;

@UtilityClass
public class UserMapper {
    public UserDto toUserDto(User saveUser) {
        return UserDto.builder()
                .id(saveUser.getId())
                .name(saveUser.getName())
                .email(saveUser.getEmail())
                .build();
    }

    public User toUser(NewUserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();
    }
}
