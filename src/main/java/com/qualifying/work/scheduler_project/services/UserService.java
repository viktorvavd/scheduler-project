package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserEntity> getAllUsersEntities();
    List<UserDto> getAllUsers();

    UserDto createUser(UserDto user);
    UserDto getUserById(UUID id);
    UserDto updateUser(UserDto user);
    void deleteUser(UUID id);
}
