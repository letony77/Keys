package com.hibernate.web;

import com.hibernate.web.dao.UserRepository;
import com.hibernate.web.entities.Role;
import com.hibernate.web.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Instant;
import java.util.Date;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)

public class WebApplication {

	public static void main(String[] args) {


		SpringApplication.run(WebApplication.class, args);

//		ApplicationContext ctx = SpringApplication.run(WebApplication.class, args);

/*
		RoleRepository RoleDAO = ctx.getBean(RoleRepository.class);

		Role role_Emp = new Role("Employeur");
		Role role_Stage = new Role("Stagiaire");
		RoleDAO.save(role_Emp);
		RoleDAO.save(role_Stage);
		System.out.println(role_Emp.getRole_id());
		System.out.println(role_Emp.getName_role());
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		userRepository.save(new User("test@test", "Le", "Tony", "0661912955", role_Emp));
		userRepository.save(new User("test@test", "Le", "Capgemini", "0661912955", role_Stage));
		userRepository.save(new User("test@test", "Le", "Capgemini", "0661912955", role_Emp));
*/



	}}
