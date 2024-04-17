package com.qualifying.work.scheduler_project.mappers;

import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserEntity userDtoToEntity(UserDto userDto);
    UserDto userEntityToDto(UserEntity userDto);
}
