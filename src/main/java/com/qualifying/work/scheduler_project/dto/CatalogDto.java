package com.qualifying.work.scheduler_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CatalogDto {
    private UUID id;

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private UUID ownerID;
    private UUID parentCatalogID;
    private String parentCatalogName;
    private String code;
    private List<GroupDto> groups;
}
