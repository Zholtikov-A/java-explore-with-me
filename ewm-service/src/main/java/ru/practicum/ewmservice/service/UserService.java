package ru.practicum.ewmservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.UserCreateRequestDto;
import ru.practicum.ewmservice.dto.UserDto;
import ru.practicum.ewmservice.exceptions.ValidationIdException;
import ru.practicum.ewmservice.mapper.UserMapper;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto createUser(UserCreateRequestDto userRequest) {
        User user = UserMapper.toUser(userRequest);
        User saveUser = userRepository.save(user);
        return UserMapper.toUserDto(saveUser);
    }

    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        List<User> userList = new ArrayList<>();

        if (ids == null || ids.isEmpty()) {
            userList = userRepository.findAll(PageRequest.of(from, size)).getContent();
        } else {
            userList = userRepository.findAllByIdIn(ids, PageRequest.of(from, size)).getContent();
        }

        return userList.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ValidationIdException("Compilation with id = " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }

    public User checkUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ValidationIdException("User with id = " + userId + " not found"));
    }
}
