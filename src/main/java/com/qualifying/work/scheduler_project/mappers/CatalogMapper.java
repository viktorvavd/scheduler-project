package com.qualifying.work.scheduler_project.mappers;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.entities.Catalog;
import org.mapstruct.Mapper;

@Mapper
public interface CatalogMapper {
    CatalogDto entityToDto(Catalog catalog);
    Catalog dtoToEntity(CatalogDto catalogDto);
}
