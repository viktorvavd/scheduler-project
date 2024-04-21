package com.qualifying.work.scheduler_project.controllers;

import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class RegisterController {
    final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        userDto.setGroups(new ArrayList<>());
        userDto.setUserCatalogList(new ArrayList<>());
       UserDto user = userService.createUser(userDto);
       return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
