package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.dto.UserCatalogDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserEntity> getAllUsersEntities();
    List<UserDto> getAllUsers();

    UserDto createUser(UserDto user);
    UserDto getUserById(UUID id);
    UserEntity getUserEntityById(UUID id);
    UserDto updateUser(UserDto user);
    void deleteUser(UUID id);

    void addAdmin(UUID catalogId, String userLogin);
    List<UserDto> getAdmins(UUID catalogId);
    void removeAdmin(UUID catalogId, String userLogin);

    List<CatalogDto> getAllUserCatalogs(UUID userId);
    void addNewCatalog(UUID userId, CatalogDto catalogDto, boolean isAdmin);
    void removeCatalog(UUID userID, UUID catalogID);
    void enrollUserToGroup(UUID userID, UUID groupID);
    void removeUserFromGroup(UUID userID, UUID groupID);
    List<GroupDto> getAllUserGroups(UUID userID);

    UserDto getCatalogOwner(UUID catalogId);
}
