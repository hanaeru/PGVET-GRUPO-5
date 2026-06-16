package com.pgvet.vacuna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VacunaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacunaServiceApplication.class, args);
	}

}
