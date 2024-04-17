package com.qualifying.work.scheduler_project.mappers;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.entities.Catalog;
import com.qualifying.work.scheduler_project.entities.UserEntity;
import com.qualifying.work.scheduler_project.repositories.CatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import com.qualifying.work.scheduler_project.services.CatalogService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Component
@RequiredArgsConstructor
public class CatalogMapper {
    final UserMapper userMapper;
    final UserRepository userRepository;
    final CatalogRepository catalogRepository;
    public CatalogDto entityToDto(Catalog catalog){
        if(catalog == null){
            return null;
        }

        CatalogDto catalogDto = new CatalogDto();
        catalogDto.setId(catalog.getId());
        catalogDto.setName(catalog.getName());
        catalogDto.setOwnerID(catalog.getOwner().getId());
        if(catalog.getParentCatalog() == null){
            catalogDto.setParentCatalogID(null);
        }else{
            catalogDto.setParentCatalogID(catalog.getParentCatalog().getId());
        }
        return catalogDto;
    }
    public Catalog dtoToEntity(CatalogDto catalogDto){
        if(catalogDto == null){
            return null;
        }
        Catalog parrentCatalog;
        if(catalogDto.getParentCatalogID() == null){
            parrentCatalog = null;
        }else{
           parrentCatalog = catalogRepository.findById(catalogDto.getId()).orElseThrow();
        }

        UserEntity owner = userRepository.findById(catalogDto.getOwnerID()).orElseThrow();

        return new Catalog(
                catalogDto.getId(),
                catalogDto.getName(),
                owner,
                parrentCatalog
        );
    }
}
