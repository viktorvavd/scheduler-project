package com.qualifying.work.scheduler_project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupDto {
    private UUID id;
    @NotNull
    private String name;

    private CatalogDto catalog;

}
