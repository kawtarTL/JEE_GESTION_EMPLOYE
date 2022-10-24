package com.example.gesemp;

import com.example.gesemp.models.AppRole;
import com.example.gesemp.models.Employee;
import com.example.gesemp.models.Genre;
import com.example.gesemp.models.Type;
import com.example.gesemp.servicesImpl.AppRoleServiceImpl;
import com.example.gesemp.servicesImpl.EmployeeServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)

public class GesempApplication implements WebMvcConfigurer {

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(GesempApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowedOrigins("http://localhost:4200","http://localhost:8080");
		WebMvcConfigurer.super.addCorsMappings(registry);
	}

	@Bean
	CommandLineRunner start(AppRoleServiceImpl appRoleService){
		return (args -> {
//			Date birthDate= new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH).parse("13-06-2000");
//			Date startWork= new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.FRENCH).parse("17-9-2019 15:30");
//
//			Employee admin= new Employee(null, "Bidias", "Ken", "bidiasken@gesemp.com", 350000, "admin", Genre.Mr, Type.CDI, "assets/users/01.jpeg", birthDate, startWork);
//			employeeService.addEmployee(admin);

			AppRole roleAdmin= new AppRole(null, "ADMIN");
			appRoleService.AddRole(roleAdmin);
//			employeeService.addRoleToEmployee(admin.getFirstName(), roleAdmin.getName());
			AppRole roleUser= new AppRole(null, "USER");
			appRoleService.AddRole(roleUser);

		});
	}

}
