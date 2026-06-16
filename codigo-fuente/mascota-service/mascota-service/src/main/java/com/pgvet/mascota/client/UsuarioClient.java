package com.pgvet.mascota.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pgvet.mascota.dto.UsuarioDTO;

// Cliente Feign para consultar el servicio de usuarios
// Esto permite llamar a Usuario-Service sin entrar a su base de datos
@FeignClient(
        name = "usuario-client",
        url = "${usuario.service.url}"
)
public interface UsuarioClient {

    // Busca un usuario por ID en Usuario-Service
    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDTO buscarPorId(@PathVariable("id") Long id);
}
