package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.Catalog;
import com.qualifying.work.scheduler_project.entities.UserCatalog;
import com.qualifying.work.scheduler_project.entities.UserEntity;
import com.qualifying.work.scheduler_project.mappers.CatalogMapper;
import com.qualifying.work.scheduler_project.repositories.CatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserCatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService{
    final CatalogRepository catalogRepository;
    final CatalogMapper catalogMapper;
    final UserCatalogRepository userCatalogRepository;
    final UserRepository userRepository;
    @Override
    public List<CatalogDto> getAllCatalogs() {
        return null;
    }

    @Override
    public List<Catalog> getAllCatalogEntities() {
        return null;
    }

    @Override
    public CatalogDto createCatalog(CatalogDto catalogDto) {
        Catalog catalog = catalogMapper.dtoToEntity(catalogDto);
        catalog = catalogRepository.save(catalog);
//        UserEntity owner = catalog.getOwner();
//
//        UserCatalog userCatalog = new UserCatalog();
//        userCatalog.setUser(owner);
//        userCatalog.setCatalog(catalog);
//        userCatalog.setAmin(true);
//
////        userCatalog =
//        owner.getUserCatalogList().add(userCatalogRepository.save(userCatalog));
//        userRepository.save(owner);
//        catalog = catalogRepository.save(catalog);
        return catalogMapper.entityToDto(catalog);
    }

    @Override
    public CatalogDto updateCatalog(CatalogDto catalogDto) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public CatalogDto findById(UUID id) {
        return null;
    }

    @Override
    public UserDto getOwner(UUID catalogId) {
        CatalogDto catalogDto = findById(catalogId);
        return null;
    }

    @Override
    public CatalogDto getParentCatalog() {
        return null;
    }
}
