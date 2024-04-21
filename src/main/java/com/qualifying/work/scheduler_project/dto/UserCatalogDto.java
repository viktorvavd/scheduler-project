package com.qualifying.work.scheduler_project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCatalogDto {
    @Null
    private UUID id;

    @NotNull
    private UUID userID;
    @NotNull
    private UUID catalogID;

    @NotNull
    private boolean isAmin;
}
