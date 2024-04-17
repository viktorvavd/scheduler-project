package com.qualifying.work.scheduler_project.mappers;

import com.qualifying.work.scheduler_project.dto.UserCatalogDto;
import com.qualifying.work.scheduler_project.entities.UserCatalog;
import org.mapstruct.Mapper;

@Mapper
public interface UserCatalogMapper {
    UserCatalogDto entityToDto(UserCatalog userCatalog);
    UserCatalog dtoToEntity(UserCatalogDto userCatalogDto);
}
