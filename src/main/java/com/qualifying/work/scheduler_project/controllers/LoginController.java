package com.qualifying.work.scheduler_project.controllers;

import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid UserDto userDto) {
        UserDto user = userService.getUserByLoginAndPassword(userDto.getLogin(), userDto.getPassword());
        return ResponseEntity.ok(user);
    }
}
