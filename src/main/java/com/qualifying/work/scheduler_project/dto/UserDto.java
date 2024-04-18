package com.qualifying.work.scheduler_project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;

    @NotNull
    private String login;
    @NotNull
    private String password;
    private List<UserCatalogDto> userCatalogList;
    private List<GroupDto> groups;
}
