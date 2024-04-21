package com.qualifying.work.scheduler_project.controllers;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.EventDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.services.CatalogService;
import com.qualifying.work.scheduler_project.services.EventService;
import com.qualifying.work.scheduler_project.services.GroupService;
import com.qualifying.work.scheduler_project.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    final UserService userService;
    final CatalogService catalogService;
    final GroupService groupService;
    final EventService eventService;
    @GetMapping("/{userId}/schedule")
    public ResponseEntity<List<EventDto>> getUserSchedule(@PathVariable UUID userId,
                                                          @RequestParam String startDate,
                                                          @RequestParam  String endDate) {
        DateFormat formater =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = new Date();
        Date end = new Date();
        try {
            start = new Date(formater.parse(startDate).getTime());
            end = new Date(formater.parse(endDate).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<EventDto> events = userService.getUserEvents(userId, start, end);
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{userId}/enroll_catalog")
    public ResponseEntity<UserDto> enrollCatalog(@PathVariable UUID userId, @RequestParam String catalogCode){
        userService.enrollUserToCatalog(userId, catalogCode);
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/{userId}/get_catalogs")
    public ResponseEntity<List<CatalogDto>> getUserRootCatalogs(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.getUserRootCatalogs(userId));
    }

    @PostMapping("/{userId}/get_catalogs/create")
    public ResponseEntity<CatalogDto> createUserRootCatalog(@PathVariable UUID userId,
                                                            @Valid @RequestBody CatalogDto catalogDto){
        catalogDto.setOwnerID(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.createCatalog(catalogDto));
    }

    @GetMapping("/{userId}/get_catalogs/catalog")
    public ResponseEntity<List<CatalogDto>> getChildCatalogs(@PathVariable UUID userId,
                                                             @RequestParam UUID catalogId){
        return ResponseEntity.ok(catalogService.getChildCatalogs(catalogId));
    }


    @PostMapping("/{userId}/get_catalogs/catalog/create")
    public ResponseEntity<CatalogDto> createChildCatalog(@PathVariable UUID userId,
                                                         @Valid @RequestBody CatalogDto catalogDto){
        catalogDto.setOwnerID(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.createCatalog(catalogDto));
    }

    @PutMapping("/{userId}/get_catalogs/catalog/updateName")
    public ResponseEntity<CatalogDto> updateCatalogName(@PathVariable UUID userId,
                                                        @Valid @RequestBody CatalogDto catalogDto){
        return ResponseEntity.status(HttpStatus.OK).body(catalogService.updateCatalog(catalogDto));
    }

    @DeleteMapping("/{userId}/get_catalogs/catalog/remove")
    public ResponseEntity<Void> removeCatalog(@PathVariable UUID userId,
                                                        @RequestParam UUID catalogId){
        userService.removeCatalog(userId, catalogId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{userId}/get_catalogs/catalog/groups")
    public ResponseEntity<List<GroupDto>> getGroupsInCatalog(@PathVariable UUID userId, @RequestParam UUID catalogId){
        return ResponseEntity.ok(catalogService.getGroupsByCatalogId(catalogId));
    }

    @PutMapping("/{userId}/get_catalogs/catalog/add_admin")
    public ResponseEntity<Void> addAdmin(@PathVariable UUID userId,
                                                        @RequestParam UUID catalogId, @RequestParam String newAdminLogin){
        userService.addAdmin(catalogId, newAdminLogin);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/get_catalogs/catalog/get_admins")
    public ResponseEntity<List<String>> getAdmins(@PathVariable UUID userId,
                                         @RequestParam UUID catalogId){
       List<String> adminNames =  userService.getAdmins(catalogId).stream().map(UserDto::getLogin).toList();
        return ResponseEntity.ok(adminNames);
    }

    @PutMapping("/{userId}/get_catalogs/catalog/remove_admin")
    public ResponseEntity<Void> removeAdmin(@PathVariable UUID userId,
                                         @RequestParam UUID catalogId, @RequestParam String removableAdminLogin){
        if(userService.getUserById(userId).getLogin().equals(removableAdminLogin)){
            return ResponseEntity.badRequest().build();
        }
        userService.removeAdmin(catalogId, removableAdminLogin);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/get_catalogs/catalog/groups/enroll_group")
    public ResponseEntity<Void> enrollGroup(@PathVariable UUID userId,
                                            @RequestParam UUID groupId){
        userService.enrollUserToGroup(userId, groupId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/get_catalogs/catalog/create_group")
    public ResponseEntity<GroupDto> createGroup(@PathVariable UUID userId,
                                            @RequestParam UUID catalogId, @Valid @RequestBody GroupDto groupDto){
        groupDto.setEvents(new ArrayList<>());
        groupDto = groupService.createGroup(groupDto);
        catalogService.addGroup(catalogId,groupDto.getId());
        userService.enrollUserToGroup(userId,groupDto.getId());
        return ResponseEntity.ok(groupDto);
    }
    @PutMapping("/{userId}/get_catalogs/catalog/group")
    public ResponseEntity<List<EventDto>> getGroupEvents(@PathVariable UUID userId,
                                              @RequestParam UUID groupId){
        List<EventDto> eventDtos = groupService.getGroupById(groupId).getEvents();
        return ResponseEntity.ok(eventDtos);
    }

    @DeleteMapping("/{userId}/get_catalogs/catalog/group/remove")
    public ResponseEntity<Void> removeGroup(@PathVariable UUID userId, @RequestParam UUID groupId){
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/get_catalogs/catalog/group/add_event")
    public ResponseEntity<EventDto> addEvent(@PathVariable UUID userId,
                                             @RequestParam UUID groupId,
                                             @RequestBody EventDto eventDto){
        eventDto = eventService.createEvent(eventDto,groupId);
        return ResponseEntity.ok(eventDto);
    }

    @PutMapping("/{userId}/get_catalogs/catalog/group/edit_event")
    public ResponseEntity<EventDto> editEvent(@PathVariable UUID userId, @RequestParam UUID group_id,
                                             @RequestBody EventDto eventDto){
        eventDto = eventService.updateEvent(eventDto, group_id);
        return ResponseEntity.ok(eventDto);
    }

    @DeleteMapping("/{userId}/get_catalogs/catalog/group/remove_event")
    public ResponseEntity<Void> removeEvent(@PathVariable UUID userId, @RequestParam UUID eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }




}
