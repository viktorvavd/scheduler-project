package com.qualifying.work.scheduler_project;

import com.qualifying.work.scheduler_project.dto.CatalogDto;
import com.qualifying.work.scheduler_project.dto.EventDto;
import com.qualifying.work.scheduler_project.dto.GroupDto;
import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.entities.Catalog;
import com.qualifying.work.scheduler_project.entities.UserCatalog;
import com.qualifying.work.scheduler_project.mappers.CatalogMapper;
import com.qualifying.work.scheduler_project.mappers.GroupMapper;
import com.qualifying.work.scheduler_project.mappers.UserMapper;
import com.qualifying.work.scheduler_project.repositories.CatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserCatalogRepository;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import com.qualifying.work.scheduler_project.services.CatalogService;
import com.qualifying.work.scheduler_project.services.EventService;
import com.qualifying.work.scheduler_project.services.GroupService;
import com.qualifying.work.scheduler_project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
	final GroupService groupService;
	final GroupMapper groupMapper;
	final EventService eventService;

	public static void main(String[] args) {
		SpringApplication.run(SchedulerProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
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
		admin.setPassword("1111");
		admin.setUserCatalogList(new ArrayList<>());
		admin.setGroups(new ArrayList<>());
		admin = userService.createUser(admin);

//
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
//
//		userService.enrollUserToCatalog(user.getId(), catalog1.getCode());

		GroupDto groupDto = new GroupDto(
				null,
				"group1",
				new ArrayList<>()
		);
		groupDto = groupService.createGroup(groupDto);
		catalogService.addGroup(catalog1.getId(), groupDto.getId());
		userService.enrollUserToGroup(admin.getId(), groupDto.getId());

		DateFormat formater =
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = "2024-4-20 12:30:00";
		String endDate = "2024-4-20 13:30:00";
		String repeat = "2024-5-12 00:00:00";


		EventDto eventDto = new EventDto(
				null,
				"Exam",
				new Date(formater.parse(strDate).getTime()),
				new Date(formater.parse(endDate).getTime()),
				new Date(formater.parse(repeat).getTime())
		);
		eventDto = eventService.createEvent(eventDto, groupDto.getId());
		EventDto eventDto2 = new EventDto(
				eventDto.getId(),
				"New exam",
				new Date(formater.parse(strDate).getTime()),
				new Date(formater.parse(endDate).getTime()),
				new Date(formater.parse(repeat).getTime())
		);
		eventService.updateEvent(eventDto2, groupDto.getId());


		String start = "2024-4-19 00:00:00";
		String end = "2024-6-21 13:30:00";
		System.out.println(Arrays.toString(userService.getUserEvents(
                admin.getId(),
                formater.parse(start),
                formater.parse(end)).toArray())
		);
		System.out.println(admin.getId());
		System.out.println(user.getId());


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
