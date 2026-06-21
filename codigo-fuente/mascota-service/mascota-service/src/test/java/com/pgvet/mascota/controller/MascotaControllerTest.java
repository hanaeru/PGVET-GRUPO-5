package com.pgvet.mascota.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgvet.mascota.dto.MascotaCreateDTO;
import com.pgvet.mascota.dto.MascotaDTO;
import com.pgvet.mascota.service.MascotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MascotaControllerTest {

    @Mock
    private MascotaService mascotaService;

    @InjectMocks
    private MascotaController mascotaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(mascotaController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("GET /api/v1/mascotas/{id} debe responder 200 cuando existe")
    void getPorIdDebeResponder200() throws Exception {
        // Given: el servicio encuentra la mascota.
        MascotaDTO dto = new MascotaDTO(
                1L, "Luna", "Perro", "Labrador", "Hembra", 3, 12.5, "Negro", null, 1L, true);
        when(mascotaService.buscarPorId(1L)).thenReturn(Optional.of(dto));

        // When / Then: GET devuelve 200 con el nombre en JSON.
        mockMvc.perform(get("/api/v1/mascotas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Luna"));
    }

    @Test
    @DisplayName("GET /api/v1/mascotas/{id} debe responder 404 cuando no existe")
    void getPorIdDebeResponder404() throws Exception {
        // Given: el servicio no encuentra la mascota.
        when(mascotaService.buscarPorId(99L)).thenReturn(Optional.empty());

        // When / Then: el controller responde 404 con el mensaje definido.
        mockMvc.perform(get("/api/v1/mascotas/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Mascota no encontrada"));
    }

    @Test
    @DisplayName("POST /api/v1/mascotas debe responder 201 al crear mascota")
    void postDebeResponder201() throws Exception {
        // Given: DTO válido y servicio que simula el guardado.
        MascotaCreateDTO dtoEntrada = new MascotaCreateDTO();
        dtoEntrada.setNombre("Luna");
        dtoEntrada.setEspecie("Perro");
        dtoEntrada.setRaza("Labrador");
        dtoEntrada.setSexo("Hembra");
        dtoEntrada.setEdad(3);
        dtoEntrada.setPeso(12.5);
        dtoEntrada.setColor("Negro");
        dtoEntrada.setTutorId(1L);

        MascotaDTO dtoSalida = new MascotaDTO(
                1L, "Luna", "Perro", "Labrador", "Hembra", 3, 12.5, "Negro", null, 1L, true);
        when(mascotaService.guardar(any(MascotaCreateDTO.class))).thenReturn(dtoSalida);

        // When / Then: POST devuelve 201 y el nombre creado.
        mockMvc.perform(post("/api/v1/mascotas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Luna"));
    }
}
