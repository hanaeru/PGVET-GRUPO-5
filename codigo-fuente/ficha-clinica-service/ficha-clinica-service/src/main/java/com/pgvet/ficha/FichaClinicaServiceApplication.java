package com.pgvet.ficha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FichaClinicaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FichaClinicaServiceApplication.class, args);
	}

}
