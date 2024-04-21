package com.qualifying.work.scheduler_project;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.services.CatalogService;
import com.qualifying.work.scheduler_project.services.GroupService;
import com.qualifying.work.scheduler_project.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest

public class ServiceTests {
    @Autowired
    UserService userService;
    @Autowired
    CatalogService catalogService;

    @Autowired
    GroupService groupService;

    @Test
    public void ownerCatalogTest(){
        UserDto user = new UserDto();
        user.setId(null);
        user.setLogin("agdjrdkflogin");
        user.setPassword("1111");
        user.setUserCatalogList(new ArrayList<>());
        user.setGroups(new ArrayList<>());
        user = userService.createUser(user);

        UserDto admin = new UserDto();
        admin.setId(null);
        admin.setLogin(";oug;badmin");
        admin.setPassword("1234");
        admin.setUserCatalogList(new ArrayList<>());
        admin.setGroups(new ArrayList<>());
        admin = userService.createUser(admin);

        CatalogDto catalog1 = new CatalogDto(
                null,
                "catalog1",
                user.getId(),
                null,
                null,
                null,
                new ArrayList<>()
        );
        catalog1 = catalogService.createCatalog(catalog1);
        CatalogDto catalog2 = new CatalogDto(
                null,
                "catalog2",
                admin.getId(),
                null,
                null,
                null,
                new ArrayList<>()
        );
        catalog2 = catalogService.createCatalog(catalog2);

        UUID ownerID1 = userService.getCatalogOwner(catalog1.getId()).getId();
        UUID ownerID2 = userService.getCatalogOwner(catalog2.getId()).getId();

        assertEquals(ownerID1,user.getId());
        assertEquals(ownerID2,admin.getId());

//        userService.addNewCatalog(user.getId(), catalog1,false);
    }

    @Test
    public void addAndRemoveCatalogTest(){
        UserDto user = new UserDto();
        user.setId(null);
        user.setLogin("qwwejvnweovi");
        user.setPassword("1111");
        user.setUserCatalogList(new ArrayList<>());
        user.setGroups(new ArrayList<>());
        user = userService.createUser(user);

        UserDto admin = new UserDto();
        admin.setId(null);
        admin.setLogin("ADMINISTRATOR");
        admin.setPassword("1234");
        admin.setUserCatalogList(new ArrayList<>());
        admin.setGroups(new ArrayList<>());
        admin = userService.createUser(admin);


        CatalogDto catalog1 = new CatalogDto(
                null,
                "catalog143342",
                admin.getId(),
                null,
                null,
                null,
                new ArrayList<>()
        );
        catalog1 = catalogService.createCatalog(catalog1);
        CatalogDto catalog2 = new CatalogDto(
                null,
                "catalog1488",
                admin.getId(),
                catalog1.getId(),
                null,
                null,
                new ArrayList<>()
        );
        catalog2 = catalogService.createCatalog(catalog2);


        userService.addNewCatalog(user.getId(), catalog1,false);
        userService.addNewCatalog(user.getId(), catalog2,false);

        assertEquals(userService.getAdmins(catalog1.getId()).size(), 1);
        assertEquals(userService.getAdmins(catalog1.getId()).get(0).getLogin(), admin.getLogin());
        assertEquals(userService.getAllUserCatalogs(user.getId()).size(), 2);
        assertEquals(userService.getUserRootCatalogs(user.getId()).size(), 1);

        userService.removeCatalog(user.getId(), catalog1.getId());
        assertEquals(userService.getAllUserCatalogs(user.getId()).size(), 0);
    }
    @Test
    public void enrollAndRemoveGroupTest(){
        UserDto user = new UserDto();
        user.setId(null);
        user.setLogin("user");
        user.setPassword("123qwe");
        user.setUserCatalogList(new ArrayList<>());
        user.setGroups(new ArrayList<>());
        user = userService.createUser(user);

        GroupDto groupDto1 = new GroupDto(
                null,
                "English group1",
                new ArrayList<>()

        );
        groupDto1 = groupService.createGroup(groupDto1);
        GroupDto groupDto2 = new GroupDto(
                null,
                "Programing group2",
                new ArrayList<>()

        );
        groupDto2 = groupService.createGroup(groupDto2);

        userService.enrollUserToGroup(user.getId(), groupDto1.getId());
        userService.enrollUserToGroup(user.getId(), groupDto2.getId());
        assertEquals(userService.getAllUserGroups(user.getId()).size(), 2);


        userService.removeUserFromGroup(user.getId(), groupDto1.getId());
        assertEquals(userService.getAllUserGroups(user.getId()).size(), 1);
        assertEquals(userService.getAllUserGroups(user.getId()).get(0).getId(), groupDto2.getId());
    }

    @Test
    public void addRemoveAdmin(){
        UserDto admin = new UserDto();
        admin.setId(null);
        admin.setLogin("adminOLD");
        admin.setPassword("1234");
        admin.setUserCatalogList(new ArrayList<>());
        admin.setGroups(new ArrayList<>());
        admin = userService.createUser(admin);

        UserDto user = new UserDto();
        user.setId(null);
        user.setLogin("NEW_admin");
        user.setPassword("1111");
        user.setUserCatalogList(new ArrayList<>());
        user.setGroups(new ArrayList<>());
        user = userService.createUser(user);

        CatalogDto catalog1 = new CatalogDto(
                null,
                "catalog1",
                admin.getId(),
                null,
                null,
                null,
                new ArrayList<>()
        );
        catalog1 = catalogService.createCatalog(catalog1);
        CatalogDto catalog2 = new CatalogDto(
                null,
                "catalog2",
                admin.getId(),
                null,
                null,
                null,
                new ArrayList<>()
        );
        catalog2 = catalogService.createCatalog(catalog2);

        assertEquals(userService.getAdmins(catalog1.getId()).size(), 1);
        assertEquals(userService.getAdmins(catalog2.getId()).size(), 1);

        userService.addAdmin(catalog1.getId(), user.getLogin());
        assertEquals(userService.getAdmins(catalog1.getId()).size(), 2);

        userService.removeAdmin(catalog1.getId(), user.getLogin());
        assertEquals(userService.getAdmins(catalog1.getId()).size(), 1);
        assertEquals(userService.getAdmins(catalog1.getId()).get(0).getId(), admin.getId());

    }
}
