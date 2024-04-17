package com.qualifying.work.scheduler_project;

import com.qualifying.work.scheduler_project.dto.UserDto;
import com.qualifying.work.scheduler_project.repositories.UserRepository;
import com.qualifying.work.scheduler_project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SchedulerProjectApplication implements CommandLineRunner {

	final UserRepository userRepository;
	final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(SchedulerProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserDto user = new UserDto();
		user.setId(null);
		user.setLogin("login");
		user.setPassword("1111");
		user.setUserCatalogList(null);
		UserDto userDto = userService.createUser(user);

		System.out.println(userService.getUserById(userDto.getId()).getLogin());
		System.out.println(userService.getUserById(userDto.getId()).getPassword());
	}
}
