package com.pgvet.receta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pgvet.receta.dto.FichaClinicaDTO;

// Cliente Feign para consultar Ficha-Clinica-Service
@FeignClient(name = "ficha-clinica-client", url = "${ficha-clinica.service.url}")
public interface FichaClinicaClient {

    @GetMapping("/api/v1/fichas-clinicas/{id}")
    FichaClinicaDTO buscarPorId(@PathVariable("id") Long id);
}
