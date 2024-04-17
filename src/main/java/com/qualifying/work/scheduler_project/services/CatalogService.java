package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.Catalog;

import java.util.List;
import java.util.UUID;

public interface CatalogService {
    List<CatalogDto> getAllCatalogs();
    List<Catalog> getAllCatalogEntities();
    CatalogDto createCatalog(CatalogDto catalogDto);
    CatalogDto updateCatalog(CatalogDto catalogDto);
    void deleteById(UUID id);
    CatalogDto findById(UUID id);

    UserDto getOwner(UUID catalogId);
    CatalogDto getParentCatalog();
}
