package com.pgvet.mascota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MascotaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MascotaServiceApplication.class, args);
	}

}
