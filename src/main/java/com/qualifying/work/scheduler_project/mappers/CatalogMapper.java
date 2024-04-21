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
    final GroupMapper groupMapper;
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
            catalogDto.setParentCatalogName(null);
        }else{
            catalogDto.setParentCatalogID(catalog.getParentCatalog().getId());
            catalogDto.setParentCatalogName(catalog.getParentCatalog().getName());
        }
        catalogDto.setGroups(catalog.getGroups().stream().map(groupMapper::entityToDto).toList());
        return catalogDto;
    }

    public Catalog dtoToEntity(CatalogDto catalogDto){
        if(catalogDto == null){
            return null;
        }
        Catalog parrentCatalog = new Catalog();
        if(catalogDto.getParentCatalogID() == null){
            parrentCatalog = null;
        }else{
           parrentCatalog = catalogRepository.findById(catalogDto.getParentCatalogID()).orElseThrow();
        }

        UserEntity owner = userRepository.findById(catalogDto.getOwnerID()).orElseThrow();

        return new Catalog(
                catalogDto.getId(),
                catalogDto.getName(),
                owner,
                catalogDto.getCode(),
                parrentCatalog,
                catalogDto.getGroups().stream().map(groupMapper::dtoToEntity).toList()
        );
    }
}
