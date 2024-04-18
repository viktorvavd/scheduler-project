package com.qualifying.work.scheduler_project;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.Catalog;
import com.qualifying.work.scheduler_project.entities.UserCatalog;
import com.qualifying.work.scheduler_project.mappers.CatalogMapper;
import com.qualifying.work.scheduler_project.mappers.UserMapper;
import com.qualifying.work.scheduler_project.repositories.CatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserCatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import com.qualifying.work.scheduler_project.services.CatalogService;
import com.qualifying.work.scheduler_project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class SchedulerProjectApplication implements CommandLineRunner {

	final UserRepository userRepository;
	final UserService userService;
	final UserMapper userMapper;
	final UserCatalogRepository userCatalogRepository;
	final CatalogRepository catalogRepository;
	final CatalogService catalogService;
	final CatalogMapper catalogMapper;

	public static void main(String[] args) {
		SpringApplication.run(SchedulerProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		UserDto user = new UserDto();
//		user.setId(null);
//		user.setLogin("login");
//		user.setPassword("1111");
//		user.setUserCatalogList(new ArrayList<>());
//		user.setGroups(new ArrayList<>());
//		UserDto userDto = userService.createUser(user);
//
//		UserDto userAdmin = new UserDto();
//		userAdmin.setId(null);
//		userAdmin.setLogin("admin");
//		userAdmin.setPassword("1234");
//		userAdmin.setUserCatalogList(new ArrayList<>());
//		userAdmin.setGroups(new ArrayList<>());
//		userAdmin = userService.createUser(userAdmin);
//
//		CatalogDto catalog1 = new CatalogDto(
//				null,
//				"catalog1",
//				userDto.getId(),
//				null,
//				null,
//				new ArrayList<>()
//		);
//		catalog1 = catalogService.createCatalog(catalog1);
//		CatalogDto catalog2 = new CatalogDto(
//				null,
//				"catalog2",
//				userDto.getId(),
//				null,
//				null,
//				new ArrayList<>()
//		);
//		catalog2 = catalogService.createCatalog(catalog2);
//		CatalogDto catalog3 = new CatalogDto(
//				null,
//				"catalog3",
//				userAdmin.getId(),
//				null,
//				null,
//				new ArrayList<>()
//		);
//		catalog3 = catalogService.createCatalog(catalog3);
//
//		userCatalogRepository.save(
//				new UserCatalog(
//						null,
//						userMapper.userDtoToEntity(userAdmin),
//						catalogMapper.dtoToEntity(catalog1),
//						true
//						)
//		);
//
//		userService.addNewCatalog(userAdmin.getId(), catalog1, true);
//		userService.addNewCatalog(userAdmin.getId(), catalog2, true);
//		userService.addNewCatalog(userAdmin.getId(), catalog3, true);
//
//		userService.addNewCatalog(userDto.getId(), catalog1, true);
//		userService.addNewCatalog(userDto.getId(), catalog2, true);
//
//		UUID ownerID1 = userService.getCatalogOwner(catalog1.getId()).getId();
//		UUID ownerID2 = userService.getCatalogOwner(catalog2.getId()).getId();
//		System.out.println(userService.getUserById(ownerID1));
//		System.out.println(userService.getUserById(ownerID2));
//				System.out.println("------------------------------------------------------------------------");
//		System.out.println(Arrays.toString(userService.getAllUserCatalogs(userDto.getId()).stream().map(CatalogDto::getName).toArray()));
//
//
//
//
//		System.out.println(Arrays.toString(userService.getAllUserCatalogs(userDto.getId()).stream().map(CatalogDto::getName).toArray()));
//		System.out.println("------------------------------------------------------------------------");
//		System.out.println(userService.getAllUserCatalogs(userDto.getId()).size());
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		System.out.println(Arrays.toString(userService.getAllUserCatalogs(userAdmin.getId()).stream().map(CatalogDto::getName).toArray()));
//		System.out.println("------------------------------------------------------------------------");
//		System.out.println(userService.getAllUserCatalogs(userAdmin.getId()).size());

	}
}
