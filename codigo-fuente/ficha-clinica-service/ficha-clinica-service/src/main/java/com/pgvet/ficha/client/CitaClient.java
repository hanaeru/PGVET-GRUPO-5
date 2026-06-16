package com.pgvet.ficha.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pgvet.ficha.dto.CitaDTO;

// Cliente Feign para consultar Cita-Service
@FeignClient(name = "cita-client", url = "${cita.service.url}")
public interface CitaClient {

    @GetMapping("/api/v1/citas/{id}")
    CitaDTO buscarPorId(@PathVariable("id") Long id);
}
