package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.*;
import com.qualifying.work.scheduler_project.entities.UserEntity;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserEntity> getAllUsersEntities();
    List<UserDto> getAllUsers();

    UserDto createUser(UserDto user);
    UserDto getUserById(UUID id);
    UserDto getUserByLogin(String login);
    UserEntity getUserEntityById(UUID id);
    UserDto updateUser(UserDto user);
    void deleteUser(UUID id);

    void addAdmin(UUID catalogId, String userLogin);
    void enrollUserToCatalog(UUID userId,  String catalogCode);

    List<UserDto> getAdmins(UUID catalogId);
    void removeAdmin(UUID catalogId, String userLogin);

    List<CatalogDto> getAllUserCatalogs(UUID userId);
    List<CatalogDto> getUserRootCatalogs(UUID userId);
    void addNewCatalog(UUID userId, CatalogDto catalogDto, boolean isAdmin);
    void removeCatalog(UUID userID, UUID catalogID);
    void enrollUserToGroup(UUID userID, UUID groupID);
    void removeUserFromGroup(UUID userID, UUID groupID);
    List<GroupDto> getAllUserGroups(UUID userID);
    List<EventDto> getUserEvents(UUID userID, Date stratDate, Date endDate);
//    List<EventDto> getSchedule(Date startDate, Date endDate);

    UserDto getCatalogOwner(UUID catalogId);
}
