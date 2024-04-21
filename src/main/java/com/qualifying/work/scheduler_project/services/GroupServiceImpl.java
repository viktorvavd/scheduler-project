package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.EventDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.entities.Catalog;
import com.qualifying.work.scheduler_project.entities.GroupEntity;
import com.qualifying.work.scheduler_project.mappers.CatalogMapper;
import com.qualifying.work.scheduler_project.mappers.GroupMapper;
import com.qualifying.work.scheduler_project.repositories.CatalogRepository;
import com.qualifying.work.scheduler_project.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    final GroupRepository groupRepository;
    final GroupMapper groupMapper;

    @Override
    public List<GroupEntity> getAllGroupEntities() {
        return groupRepository.findAll();
    }

    @Override
    public List<GroupDto> getAllGroups() {
        return getAllGroupEntities().stream().map(groupMapper::entityToDto).toList();
    }


    @Override
    public GroupDto getGroupById(UUID groupID) {
        if(groupRepository.findById(groupID).isPresent()){
            return groupMapper.entityToDto(groupRepository.findById(groupID).get());
        }else{
            throw new RuntimeException("No GROUP with id:" + groupID);
        }

    }

    @Override
    public GroupEntity getGroupEntityById(UUID groupID) {
        if(groupRepository.findById(groupID).isPresent()){
            return groupRepository.findById(groupID).get();
        }else{
            throw new RuntimeException("No GROUP with id:" + groupID);
        }
    }

    @Override
    public GroupDto createGroup(GroupDto groupDto) {
        GroupEntity groupEntity = groupRepository.save(groupMapper.dtoToEntity(groupDto));
        return groupMapper.entityToDto(groupEntity);
    }

    @Override
    public GroupDto updateGroup(GroupDto groupDto) {
        if(groupRepository.findById(groupDto.getId()).isPresent()){
            GroupEntity groupEntity = groupRepository.save(groupMapper.dtoToEntity(groupDto));
            return groupMapper.entityToDto(groupEntity);
        }else{
            throw new RuntimeException("No GROUP with id:" + groupDto.getId());
        }
    }

    @Override
    public void addEvent(UUID groupId, EventDto eventDto) {
        GroupDto groupDto = getGroupById(groupId);
        List<EventDto> eventDtos = groupDto.getEvents();
        eventDtos.add(eventDto);
        groupDto.setEvents(eventDtos);
        updateGroup(groupDto);
    }

    @Override
    public void removeEvent(UUID groupId, UUID eventId) {
        GroupDto groupDto = getGroupById(groupId);
        List<EventDto> eventDtos = groupDto.getEvents();
        eventDtos.removeIf(eventDto -> eventDto.getId().equals(eventId));
        groupDto.setEvents(eventDtos);
        updateGroup(groupDto);
    }

    @Override
    public void deleteGroup(UUID groupID) {
        if(groupRepository.findById(groupID).isPresent()){
            groupRepository.deleteById(groupID);
        }else{
            throw new RuntimeException("No GROUP with id:" + groupID);
        }
    }
}
