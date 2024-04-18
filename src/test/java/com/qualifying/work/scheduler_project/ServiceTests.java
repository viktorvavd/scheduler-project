package com.qualifying.work.scheduler_project;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.services.CatalogService;
import com.qualifying.work.scheduler_project.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

public class ServiceTests {
    @Autowired
    UserService userService;
//    final UserCatalogRepository userCatalogRepository;
//    final CatalogRepository catalogRepository;
    @Autowired
CatalogService catalogService;

    @Test
    public void ownerCatalogTest(){
        UserDto user = new UserDto();
        user.setId(null);
        user.setLogin("login");
        user.setPassword("1111");
        user.setUserCatalogList(new ArrayList<>());
        user.setGroups(new ArrayList<>());
        user = userService.createUser(user);

        UserDto admin = new UserDto();
        admin.setId(null);
        admin.setLogin("admin");
        admin.setPassword("1234");
        admin.setUserCatalogList(new ArrayList<>());
        admin.setGroups(new ArrayList<>());
        admin = userService.createUser(admin);

        CatalogDto catalog1 = new CatalogDto(
                null,
                "catalog1",
                user.getId(),
                null,
                null
        );
        catalog1 = catalogService.createCatalog(catalog1);
        CatalogDto catalog2 = new CatalogDto(
                null,
                "catalog2",
                admin.getId(),
                null,
                null
        );
        catalog2 = catalogService.createCatalog(catalog2);

        UUID ownerID1 = userService.getCatalogOwner(catalog1.getId()).getId();
        UUID ownerID2 = userService.getCatalogOwner(catalog2.getId()).getId();

        assertEquals(ownerID1,user.getId());
        assertEquals(ownerID2,admin.getId());

//        userService.addNewCatalog(user.getId(), catalog1,false);
    }
}
