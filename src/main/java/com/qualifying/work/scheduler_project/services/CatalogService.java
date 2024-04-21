package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
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
    CatalogDto findByCode(String code);
    CatalogDto addGroup(UUID catalogId, UUID groupId);
    CatalogDto addNewGroup(UUID catalogId, GroupDto groupDto);
    CatalogDto getParentCatalog(UUID catalogID);
    List<GroupDto> getGroupsByCatalogId(UUID catalogID);
    List<CatalogDto> getChildCatalogs(UUID catalogID);
}
