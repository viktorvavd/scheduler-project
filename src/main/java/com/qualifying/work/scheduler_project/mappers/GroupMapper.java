package com.qualifying.work.scheduler_project.mappers;

import com.qualifying.work.scheduler_project.dto.EventDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.entities.GroupEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMapper {
    final EventMapper eventMapper;
    public GroupDto entityToDto(GroupEntity groupEntity){
        if ( groupEntity == null ) {
            return null;
        }

        GroupDto groupDto = new GroupDto();

        groupDto.setId( groupEntity.getId() );
        groupDto.setName( groupEntity.getName() );
        groupDto.setEvents( groupEntity.getEvents().stream().map(eventMapper::entityToDto).toList());

        return groupDto;
    }
   public GroupEntity dtoToEntity(GroupDto groupDto){
       if ( groupDto == null ) {
           return null;
       }

       GroupEntity groupEntity = new GroupEntity();

       groupEntity.setId( groupDto.getId() );
       groupEntity.setName( groupDto.getName() );
       groupEntity.setEvents( groupDto.getEvents().stream().map(eventMapper::dtoToEntity).toList());

       return groupEntity;
    }
}
