package com.qualifying.work.scheduler_project.mappers;

import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.entities.GroupEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto entityToDto(GroupEntity groupEntity);
    GroupEntity dtoToEntity(GroupDto groupDto);
}
