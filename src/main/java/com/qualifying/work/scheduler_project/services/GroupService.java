package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.EventDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.entities.GroupEntity;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<GroupEntity> getAllGroupEntities();
    List<GroupDto> getAllGroups();
//    List<GroupDto> getAllUserGroups(UUID userID);
//    List<GroupDto> getGroupsByCatalogId(UUID catalogID);
    GroupDto getGroupById(UUID groupID);
    GroupDto createGroup(GroupDto groupDto);
    GroupDto updateGroup(GroupDto groupDto);

    void deleteGroup(UUID groupID);

}
