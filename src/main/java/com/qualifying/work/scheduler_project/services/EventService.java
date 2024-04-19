package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.EventDto;
import com.qualifying.work.scheduler_project.entities.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {
    List<EventDto> getAllEvents();
    List<Event> getAllEventEntities();
    Event getEventEntityById(UUID eventId);
    EventDto getEventById(UUID eventId);
    EventDto createEvent(EventDto eventDto, UUID groupId);
    EventDto updateEvent(EventDto eventDto);
    void deleteEvent(UUID eventId);
}
