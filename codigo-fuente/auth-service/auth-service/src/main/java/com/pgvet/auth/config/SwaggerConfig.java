package com.pgvet.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // Registra la metadata de la API que Swagger UI muestra en pantalla
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Auth Service")
                        .version("1.0")
                        .description("Autenticación y credenciales de usuarios del sistema"));
    }
}
