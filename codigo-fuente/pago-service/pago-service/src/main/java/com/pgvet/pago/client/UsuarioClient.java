package com.pgvet.pago.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pgvet.pago.dto.UsuarioDTO;

// Cliente Feign para consultar Usuario-Service
@FeignClient(name = "usuario-client", url = "${usuario.service.url}")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDTO buscarPorId(@PathVariable("id") Long id);
}
