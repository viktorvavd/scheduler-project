package com.qualifying.work.scheduler_project.mappers;

import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    final UserCatalogMapper userCatalogMapper;
//    final GroupMapper groupMapper;
    public UserEntity userDtoToEntity(UserDto userDto){
        if ( userDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();


        userEntity.setId( userDto.getId() );
        userEntity.setLogin( userDto.getLogin() );
        userEntity.setPassword( userDto.getPassword() );
        userEntity.setUserCatalogList(userDto.getUserCatalogList().stream().map(userCatalogMapper::dtoToEntity).toList());
//        userEntity.setGroups(userDto.getGroups().stream().map(groupMapper::dtoToEntity).toList());

        return userEntity;
    }
    public UserDto userEntityToDto(UserEntity userDto){
        if ( userDto == null ) {
            return null;
        }

        UserDto userDto1 = new UserDto();

        userDto1.setId( userDto.getId() );
        userDto1.setLogin( userDto.getLogin() );
        userDto1.setPassword( userDto.getPassword() );
        userDto1.setUserCatalogList( userDto.getUserCatalogList().stream().map(userCatalogMapper::entityToDto).toList() );
//        userDto1.setGroups( userDto.getGroups().stream().map(groupMapper::entityToDto).toList() );

        return userDto1;
    }
}
