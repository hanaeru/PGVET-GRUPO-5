package com.pgvet.ficha.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pgvet.ficha.dto.MascotaDTO;

// Cliente Feign para consultar Mascota-Service
@FeignClient(name = "mascota-client", url = "${mascota.service.url}")
public interface MascotaClient {

    @GetMapping("/api/v1/mascotas/{id}")
    MascotaDTO buscarPorId(@PathVariable("id") Long id);
}
