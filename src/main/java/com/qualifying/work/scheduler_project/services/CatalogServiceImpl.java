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

import java.math.BigInteger;
import java.util.ArrayList;
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
    final GroupService groupService;
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
        if(catalogDto.getParentCatalogID() != null){
            List<CatalogDto> catalogs =
                    getChildCatalogs(catalogDto.getParentCatalogID());
            for(CatalogDto childCatalog:catalogs){
                if(childCatalog.getName().equals(catalogDto.getName())
                        && !catalogDto.getId().equals(childCatalog.getId())){
                    throw new RuntimeException("Catalog with name:" + catalogDto.getName() + " already exists");
                }
            }
        }
        catalogDto.setCode(generateCode());
        Catalog catalog = catalogMapper.dtoToEntity(catalogDto);
        catalog = catalogRepository.save(catalog);
        UserEntity owner = catalog.getOwner();

        UserCatalog userCatalog = new UserCatalog();
        userCatalog.setUser(owner);
        userCatalog.setCatalog(catalog);
        userCatalog.setAmin(true);

        userCatalogRepository.save(userCatalog);
        return catalogMapper.entityToDto(catalog);
    }

    private String generateCode(){
        String generateUUIDNo = String.format(
                "%010d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16)
        );
        return generateUUIDNo.substring( generateUUIDNo.length() - 10);
    }

    @Override
    @Transactional
    public CatalogDto updateCatalog(CatalogDto catalogDto) {
        if(catalogDto.getParentCatalogID() != null){
            List<CatalogDto> catalogs =
                    getChildCatalogs(catalogDto.getParentCatalogID());
            for(CatalogDto childCatalog:catalogs){
                if(childCatalog.getName().equals(catalogDto.getName())
                        && !catalogDto.getId().equals(childCatalog.getId())){
                    throw new RuntimeException("Catalog with name:" + catalogDto.getName() + " already exists");
                }
            }
        }
        CatalogDto oldCatalogDto = findById(catalogDto.getId());
        catalogDto.setOwnerID(oldCatalogDto.getOwnerID());
        catalogDto.setCode(oldCatalogDto.getCode());
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
    public CatalogDto findByCode(String code) {
        Catalog catalog = catalogRepository.findByCode(code);
        if(catalog == null){
            throw new RuntimeException("No CATALOG with code: "+ code);
        }
        return catalogMapper.entityToDto(catalog);
    }

    @Override
    @Transactional
    public CatalogDto addGroup(UUID catalogId, UUID groupId) {
        if (getChildCatalogs(catalogId).isEmpty()) {
            CatalogDto catalog = findById(catalogId);
            List<GroupDto> groupDtos = new ArrayList<>(catalog.getGroups());
            GroupDto groupDto = groupService.getGroupById(groupId);
            groupDtos.add(groupDto);
            catalog.setGroups(groupDtos);
            return updateCatalog(catalog);
        } else {
            throw new RuntimeException("This Catalog has child catalogs");
        }
    }

    @Override
    @Transactional
    public CatalogDto addNewGroup(UUID catalogId, GroupDto groupDto) {
        if(getChildCatalogs(catalogId).isEmpty()){
            UUID groupId = groupService.createGroup(groupDto).getId();
            return addGroup(catalogId,groupId);
        }else{
            throw new RuntimeException("This Catalog has child catalogs");
        }

    }


    @Override
    public CatalogDto getParentCatalog(UUID catalogID) {
        return findById(findById(catalogID).getParentCatalogID());
    }

    @Override
    public List<CatalogDto> getChildCatalogs(UUID catalogID) {
        if(catalogID == null || findById(catalogID) == null){
            throw new RuntimeException("Wrong Catalog id:" + catalogID);
        }
        return catalogRepository.findAllByParentCatalogId(catalogID)
                .stream().map(catalogMapper::entityToDto).toList();
    }

    @Override
    public List<GroupDto> getGroupsByCatalogId(UUID catalogID) {
        return findById(catalogID).getGroups();
    }
}
