package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.EventDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.entities.GroupEntity;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<GroupEntity> getAllGroupEntities();
    List<GroupDto> getAllGroups();
    GroupDto getGroupById(UUID groupID);
    GroupEntity getGroupEntityById(UUID groupID);
    GroupDto createGroup( GroupDto groupDto);
    GroupDto updateGroup(GroupDto groupDto);
    void addEvent(UUID groupId, EventDto eventDto);
    void removeEvent(UUID groupId, UUID eventId);

    void deleteGroup(UUID groupID);

}
