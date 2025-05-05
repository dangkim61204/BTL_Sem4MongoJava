package com.example.ProjectSem4_JavaMongo;

import com.example.ProjectSem4_JavaMongo.Service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ProjectSem4JavaMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSem4JavaMongoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService){
		return (args) -> {
			storageService.init();
		};
	}
}
