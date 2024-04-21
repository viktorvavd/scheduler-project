package com.qualifying.work.scheduler_project.mappers;

import com.qualifying.work.scheduler_project.dto.UserCatalogDto;
import com.qualifying.work.scheduler_project.entities.UserCatalog;
import com.qualifying.work.scheduler_project.repositories.CatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCatalogMapper {
    final UserRepository userRepository;
    final CatalogRepository catalogRepository;
   public UserCatalogDto entityToDto(UserCatalog userCatalog){
       if ( userCatalog == null ) {
           return null;
       }

       UserCatalogDto userCatalogDto = new UserCatalogDto();

       userCatalogDto.setId( userCatalog.getId() );
       userCatalogDto.setUserID(userCatalog.getUser().getId());
       userCatalogDto.setCatalogID(userCatalog.getCatalog().getId());
       userCatalogDto.setAmin( userCatalog.isAmin() );

       return userCatalogDto;
   }
    public UserCatalog dtoToEntity(UserCatalogDto userCatalogDto){
        if ( userCatalogDto == null ) {
            return null;
        }

        UserCatalog userCatalog = new UserCatalog();

        userCatalog.setId( userCatalogDto.getId() );
        userCatalog.setUser( userRepository.findById(userCatalogDto.getUserID()).orElseThrow() );
        userCatalog.setCatalog( catalogRepository.findById(userCatalogDto.getCatalogID()).orElseThrow() );
        userCatalog.setAmin( userCatalogDto.isAmin() );

        return userCatalog;
    }
}
