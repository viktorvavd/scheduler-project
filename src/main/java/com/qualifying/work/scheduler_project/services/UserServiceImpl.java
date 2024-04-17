package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.UserEntity;
import com.qualifying.work.scheduler_project.mappers.UserMapper;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    final UserRepository userRepository;
    final UserMapper userMapper;

    @Override
    public List<UserEntity> getAllUsersEntities() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userEntityToDto).toList();
    }

    @Override
    public UserDto createUser(UserDto user) {
        UserEntity userEntity = userMapper.userDtoToEntity(user);
        userRepository.save(userEntity);
        return userMapper.userEntityToDto(userEntity);
    }

    @Override
    public UserDto getUserById(UUID id) {
        if(userRepository.findById(id).isPresent())
            return userMapper.userEntityToDto(userRepository.findById(id).get());
        else{
            throw new RuntimeException("No such USER ID");
        }
    }

    @Override
    public UserDto updateUser(UserDto user) {
        if(userRepository.findById(user.getId()).isPresent()){
            UserEntity userEntity = userMapper.userDtoToEntity(user);
            userEntity.setId(user.getId());
            userRepository.save(userEntity);
            return userMapper.userEntityToDto(userEntity);
        }else{
            throw new RuntimeException("No such USER ID");
        }
    }

    @Override
    public void deleteUser(UUID id) {
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }else{
            throw new RuntimeException("No such USER ID");
        }
    }
}
