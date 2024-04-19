package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.Catalog;
import com.qualifying.work.scheduler_project.entities.GroupEntity;
import com.qualifying.work.scheduler_project.entities.UserCatalog;
import com.qualifying.work.scheduler_project.entities.UserEntity;
import com.qualifying.work.scheduler_project.mappers.CatalogMapper;
import com.qualifying.work.scheduler_project.mappers.GroupMapper;
import com.qualifying.work.scheduler_project.mappers.UserMapper;
import com.qualifying.work.scheduler_project.repositories.CatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserCatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import jakarta.transaction.Transactional;
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
    final GroupService groupService;
    final GroupMapper groupMapper;
//    final GroupRepository groupRepository;

    @Override
    public List<UserEntity> getAllUsersEntities() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userEntityToDto).toList();
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto user) {
        if(user == null){
            throw new RuntimeException("UserDTO is null");
        }
        UserEntity userEntity1 = userRepository.findByLogin(user.getLogin());
        if(userEntity1 != null){
            throw new RuntimeException("Unavailable user login: "+ user.getLogin());
        }
        UserEntity userEntity = userRepository.save(userMapper.userDtoToEntity(user));
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
    public UserEntity getUserEntityById(UUID id) {
        if(userRepository.findById(id).isPresent())
            return userRepository.findById(id).get();
        else{
            throw new RuntimeException("No such USER ID");
        }
    }

    @Override
    @Transactional
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
    @Transactional
    public void addAdmin(UUID catalogId, String userLogin) {
        UserEntity userEntity = userRepository.findByLogin(userLogin);
        addNewCatalog(userEntity.getId(), catalogService.findById(catalogId), true);
    }

    @Override
    @Transactional
    public void enrollUserToCatalog(UUID userId, String catalogCode) {
        addNewCatalog(userId, catalogService.findByCode(catalogCode), false);
    }

    @Override
    public List<UserDto> getAdmins(UUID catalogId) {
        List<UserCatalog> userCatalogList = userCatalogRepository.findAllByCatalog_Id(catalogId);
        userCatalogList.removeIf(userCatalog -> !userCatalog.isAmin());
        List<UserDto> users = new ArrayList<>();
        for(UserCatalog userCatalog: userCatalogList){
            users.add(getUserById(userCatalog.getUser().getId()));
        }
        return users;
    }

    @Override
    @Transactional
    public void removeAdmin(UUID catalogId, String userLogin) {
        if(catalogId == null || userLogin == null || userLogin.isEmpty()){
            throw new RuntimeException("userLogin or GroupId is null");
        }
        UserEntity userEntity = userRepository.findByLogin(userLogin);
        if(userEntity == null){
            throw new RuntimeException("userLogin is invalid");
        }
        userCatalogRepository.deleteByUserIdAndCatalogId(userEntity.getId(), catalogId);
    }

    @Override
    public List<CatalogDto> getAllUserCatalogs(UUID userId) {
        if(userId == null){
            throw new RuntimeException("UserID is null");
        }
//        UserEntity userEntity = userMapper.userDtoToEntity(getUserById(userId));
//        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        List<UserCatalog> userCatalogList = userCatalogRepository.findAllByUserId(userId);

        List<Catalog> catalogs = userCatalogList.stream().map(UserCatalog::getCatalog).toList();
        List<CatalogDto> catalogDtoList = new ArrayList<>();

        for (Catalog catalog : catalogs) {
            catalogDtoList.add(catalogMapper.entityToDto(catalog));
        }
        return catalogDtoList;
    }

    @Override
    @Transactional
    public void addNewCatalog(UUID userId, CatalogDto catalogDto, boolean isAdmin) {
        if(userId == null || catalogDto == null){
            throw new RuntimeException("UserID or CatalogDto is null");
        }
        UserCatalog userAndCatalog = userCatalogRepository.findByUserIdAndCatalogId(userId, catalogDto.getId());

        if (userAndCatalog == null) {
            UserEntity userEntity = userRepository.findById(userId).orElseThrow();
            if (catalogRepository.findById(catalogDto.getId()).isEmpty()) {
                catalogDto = catalogService.createCatalog(catalogDto);
            }
            userAndCatalog = new UserCatalog(
                    null,
                    userEntity,
                    catalogMapper.dtoToEntity(catalogDto),
                    isAdmin
            );
            userCatalogRepository.save(userAndCatalog);
        } else {
            userAndCatalog.setAmin(isAdmin);
            userCatalogRepository.save(userAndCatalog);
        }
    }

    @Override
    @Transactional
    public void removeCatalog(UUID userID, UUID catalogID) {
        if(userID == null || catalogID == null){
            throw new RuntimeException("UserID or CatalogID is null");
        }
        userCatalogRepository.deleteByUserIdAndCatalogId(userID, catalogID);
    }

    @Override
    public void enrollUserToGroup(UUID userID, UUID groupID) {
        if(userID == null || groupID == null){
            throw new RuntimeException("UserID or GroupId is null");
        }
        UserEntity userEntity = getUserEntityById(userID);
        List<GroupEntity> groups = userEntity.getGroups();
        groups.add(groupMapper.dtoToEntity(groupService.getGroupById(groupID)));
        userEntity.setGroups(groups);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void removeUserFromGroup(UUID userID, UUID groupID) {
        if(userID == null || groupID == null){
            throw new RuntimeException("UserID or GroupId is null");
        }
        UserEntity userEntity = getUserEntityById(userID);
        if(userEntity == null){
            throw new RuntimeException("No USER with id:" + userID);
        }
        List<GroupEntity> groups = userEntity.getGroups();
        groups.removeIf(groupEntity -> groupEntity.getId().equals(groupID));
        userEntity.setGroups(groups);
        userRepository.save(userEntity);
    }

    @Override
    public UserDto getCatalogOwner(UUID catalogId) {
        CatalogDto catalogDto = catalogMapper.entityToDto(catalogRepository.findById(catalogId).orElseThrow());
        return getUserById(catalogDto.getOwnerID());
    }

    @Override
    public List<GroupDto> getAllUserGroups(UUID userID) {
        return getUserById(userID).getGroups();
    }
}
