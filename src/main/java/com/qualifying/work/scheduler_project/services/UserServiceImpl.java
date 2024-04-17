package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.Catalog;
import com.qualifying.work.scheduler_project.entities.UserCatalog;
import com.qualifying.work.scheduler_project.entities.UserEntity;
import com.qualifying.work.scheduler_project.mappers.CatalogMapper;
import com.qualifying.work.scheduler_project.mappers.UserMapper;
import com.qualifying.work.scheduler_project.repositories.CatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserCatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    final UserRepository userRepository;
    final UserMapper userMapper;
    final UserCatalogRepository userCatalogRepository;
    final CatalogRepository catalogRepository;
    final CatalogMapper catalogMapper;
    final CatalogService catalogService;

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
        UserEntity userEntity = userRepository.save(userMapper.userDtoToEntity(user));
        UserDto userDto = userMapper.userEntityToDto(userEntity);
        return userDto;
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

    @Override
    public List<CatalogDto> getAllUserCatalogs(UUID userId) {
//        UserEntity userEntity = userMapper.userDtoToEntity(getUserById(userId));
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        List<UserCatalog> userCatalogList = userCatalogRepository.findAllByUser(userEntity);

        List<Catalog> catalogs = userCatalogList.stream().map(UserCatalog::getCatalog).toList();
        List<CatalogDto> catalogDtoList = new ArrayList<>();

        for (Catalog catalog : catalogs) {
            catalogDtoList.add(catalogMapper.entityToDto(catalog));
        }
        return catalogDtoList;
    }

    @Override
    public void addNewCatalog(UUID userId, CatalogDto catalogDto, boolean isAdmin) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        if(catalogRepository.findById(catalogDto.getId()).isEmpty()){
            catalogDto = catalogService.createCatalog(catalogDto);
        }
        UserCatalog userCatalog = new UserCatalog(
                null,
                userEntity,
                catalogMapper.dtoToEntity(catalogDto),
                isAdmin
        );
        userCatalogRepository.save(userCatalog);
//        userRepository.save(userEntity);
    }
}
