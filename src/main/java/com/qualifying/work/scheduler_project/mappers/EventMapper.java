package com.qualifying.work.scheduler_project.mappers;

import com.qualifying.work.scheduler_project.dto.EventDto;
import com.qualifying.work.scheduler_project.entities.Event;
import org.mapstruct.Mapper;

@Mapper
public interface EventMapper {
    Event dtoToEntity(EventDto eventDto);
    EventDto entityToDto(Event event);
}
