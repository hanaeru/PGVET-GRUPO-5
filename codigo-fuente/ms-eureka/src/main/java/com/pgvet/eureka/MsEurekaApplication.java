package com.pgvet.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Punto de entrada del servidor de registro Eureka.
 * Los microservicios se inscriben aquí para que el Gateway los encuentre por nombre lógico.
 */
@SpringBootApplication
// Activa el servidor central donde los microservicios publican su ubicación (IP/puerto).
@EnableEurekaServer
public class MsEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEurekaApplication.class, args);
    }
}
