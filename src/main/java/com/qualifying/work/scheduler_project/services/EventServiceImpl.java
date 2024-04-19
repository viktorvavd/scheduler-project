package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.EventDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.entities.Event;
import com.qualifying.work.scheduler_project.entities.GroupEntity;
import com.qualifying.work.scheduler_project.mappers.EventMapper;
import com.qualifying.work.scheduler_project.repositories.EventRepository;
import com.qualifying.work.scheduler_project.repositories.GroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    final EventRepository eventRepository;
    final EventMapper eventMapper;
    final GroupService groupService;
    final GroupRepository groupRepository;
    @Override
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::entityToDto).toList();
    }

    @Override
    public List<Event> getAllEventEntities() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventEntityById(UUID eventId) {
        if(eventRepository.findById(eventId).isPresent()){
            return eventRepository.findById(eventId).get();
        }else{
            throw new RuntimeException("No EVENT with id:" + eventId);
        }
    }

    @Override
    public EventDto getEventById(UUID eventId) {
        if(eventRepository.findById(eventId).isPresent()){
            return eventMapper.entityToDto(eventRepository.findById(eventId).get());
        }else{
            throw new RuntimeException("No EVENT with id:" + eventId);
        }
    }

    @Override
    @Transactional
    public EventDto createEvent(EventDto eventDto, UUID groupId) {
        Event event = eventMapper.dtoToEntity(eventDto);
        event = eventRepository.save(event);

        GroupEntity group = groupService.getGroupEntityById(groupId);
        List<Event> events = group.getEvents();
        events.add(event);

        group.setEvents(events);
        groupRepository.save(group);
        return eventMapper.entityToDto(event);
    }

    @Override
    @Transactional
    public EventDto updateEvent(EventDto eventDto) {
        if(eventRepository.findById(eventDto.getId()).isPresent()){
            Event event = eventRepository.save(eventMapper.dtoToEntity(eventDto));
            return eventMapper.entityToDto(event);
        }else{
            throw new RuntimeException("No EVENT with id:" + eventDto.getId());
        }
    }

    @Override
    public void deleteEvent(UUID eventId) {
        if(eventRepository.findById(eventId).isPresent()){
            eventRepository.deleteById(eventId);
        }else{
            throw new RuntimeException("No EVENT with id:" + eventId);
        }
    }
}
