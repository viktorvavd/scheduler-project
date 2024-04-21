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
//import java.util.*;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
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

        if(eventDto.getWeeklyRepeatUntil() != null) {
            LocalDateTime date1 = convertToLocalDateTime(eventDto.getStartTime());
            LocalDateTime date2 = convertToLocalDateTime(eventDto.getWeeklyRepeatUntil());
            Duration duration = Duration.between(date1, date2);
            for (int i = 1; i <= duration.toDays() / 7; i++) {
                EventDto repeatableEventDto = new EventDto(
                        null,
                        eventDto.getName(),
                        addDays(eventDto.getStartTime(), i * 7),
                        addDays(eventDto.getEndTime(), i * 7),
                        eventDto.getWeeklyRepeatUntil()
                );
                Event repeatableEvent = eventRepository.save(eventMapper.dtoToEntity(repeatableEventDto));
                events.add(repeatableEvent);
            }
        }
        group.setEvents(events);
        groupRepository.save(group);
        return eventMapper.entityToDto(event);
    }

    public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Override
    @Transactional
    public EventDto updateEvent(EventDto eventDto, UUID groupId) {
        if(eventRepository.findById(eventDto.getId()).isPresent()){
            String oldEventName = getEventEntityById(eventDto.getId()).getName();
            Date untilDate = getEventEntityById(eventDto.getId()).getWeeklyRepeatUntil();
            EventDto updateEvent = new EventDto();
            GroupDto group = groupService.getGroupById(groupId);
            List<EventDto> eventDtos = group.getEvents();
            for(EventDto repeatableEvent: eventDtos){
                if(repeatableEvent.getName().equals(oldEventName)
                        && untilDate.equals(repeatableEvent.getWeeklyRepeatUntil())){
                    repeatableEvent.setName(eventDto.getName());
                    eventRepository.save(eventMapper.dtoToEntity(repeatableEvent));
                }
                if(eventDto.getId().equals(repeatableEvent.getId())){
                    updateEvent = repeatableEvent;
                }
            }

            return updateEvent;
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
