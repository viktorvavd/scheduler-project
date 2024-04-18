package com.qualifying.work.scheduler_project.services;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.Catalog;
import com.qualifying.work.scheduler_project.entities.UserCatalog;
import com.qualifying.work.scheduler_project.entities.UserEntity;
import com.qualifying.work.scheduler_project.mappers.CatalogMapper;
import com.qualifying.work.scheduler_project.mappers.UserMapper;
import com.qualifying.work.scheduler_project.repositories.CatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserCatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import jakarta.transaction.Transactional;
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
    final UserMapper userMapper;
    @Override
    public List<CatalogDto> getAllCatalogs() {
        return catalogRepository.findAll().stream().map(catalogMapper::entityToDto).toList();
    }

    @Override
    public List<Catalog> getAllCatalogEntities() {
        return catalogRepository.findAll();
    }

    @Override
    @Transactional
    public CatalogDto createCatalog(CatalogDto catalogDto) {
        Catalog catalog = catalogMapper.dtoToEntity(catalogDto);
        catalog = catalogRepository.save(catalog);
        UserEntity owner = catalog.getOwner();

        UserCatalog userCatalog = new UserCatalog();
        userCatalog.setUser(owner);
        userCatalog.setCatalog(catalog);
        userCatalog.setAmin(true);

        userCatalogRepository.save(userCatalog);
//
////        userCatalog =
//        owner.getUserCatalogList().add(userCatalogRepository.save(userCatalog));
//        userRepository.save(owner);
//        catalog = catalogRepository.save(catalog);
        return catalogMapper.entityToDto(catalog);
    }

    @Override
    @Transactional
    public CatalogDto updateCatalog(CatalogDto catalogDto) {
        Catalog catalog = catalogMapper.dtoToEntity(catalogDto);
        catalog = catalogRepository.save(catalog);
        return catalogMapper.entityToDto(catalog);
    }

    @Override
    public void deleteById(UUID id) {
        try {
            catalogRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("No CATALOG with id: "+id);
        }
    }

    @Override
    public CatalogDto findById(UUID id) {
        if(catalogRepository.findById(id).isPresent()){
            return catalogMapper.entityToDto(catalogRepository.findById(id).get());
        }else{
            throw new RuntimeException("No CATALOG with id: "+ id);
        }
    }



    @Override
    public CatalogDto getParentCatalog(UUID catalogID) {
        return findById(findById(catalogID).getParentCatalogID());
    }

    @Override
    public List<CatalogDto> getChildCatalogs(UUID catalogID) {
        return null;
    }

    @Override
    public List<GroupDto> getGroupsByCatalogId(UUID catalogID) {
        return findById(catalogID).getGroups();
    }
}
