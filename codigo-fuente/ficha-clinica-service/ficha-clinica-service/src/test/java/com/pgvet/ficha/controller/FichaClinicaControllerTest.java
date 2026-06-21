package com.pgvet.ficha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pgvet.ficha.dto.FichaClinicaCreateDTO;
import com.pgvet.ficha.dto.FichaClinicaDTO;
import com.pgvet.ficha.service.FichaClinicaService;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FichaClinicaControllerTest {

    @Mock
    private FichaClinicaService fichaClinicaService;

    @InjectMocks
    private FichaClinicaController fichaClinicaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(fichaClinicaController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("GET /api/v1/fichas-clinicas/{id} debe responder 200 cuando existe")
    void getPorIdDebeResponder200() throws Exception {
        // Given: el servicio encuentra la ficha.
        FichaClinicaDTO dto = new FichaClinicaDTO(
                1L, 1L, 3L, 5L, LocalDate.of(2026, 6, 20),
                "Decaimiento", "Gastroenteritis", "Dieta blanda", null, 12.5, 38.5);
        when(fichaClinicaService.buscarPorId(1L)).thenReturn(Optional.of(dto));

        // When / Then: GET devuelve 200 con el diagnóstico.
        mockMvc.perform(get("/api/v1/fichas-clinicas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnostico").value("Gastroenteritis"));
    }

    @Test
    @DisplayName("GET /api/v1/fichas-clinicas/{id} debe responder 404 cuando no existe")
    void getPorIdDebeResponder404() throws Exception {
        // Given: el servicio no encuentra la ficha.
        when(fichaClinicaService.buscarPorId(99L)).thenReturn(Optional.empty());

        // When / Then: responde 404.
        mockMvc.perform(get("/api/v1/fichas-clinicas/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Ficha clínica no encontrada"));
    }

    @Test
    @DisplayName("POST /api/v1/fichas-clinicas debe responder 201 al crear")
    void postDebeResponder201() throws Exception {
        // Given: DTO válido y servicio simulado.
        FichaClinicaCreateDTO dtoEntrada = new FichaClinicaCreateDTO();
        dtoEntrada.setMascotaId(1L);
        dtoEntrada.setVeterinarioId(3L);
        dtoEntrada.setCitaId(5L);
        dtoEntrada.setFechaAtencion(LocalDate.of(2026, 6, 20));
        dtoEntrada.setSintomas("Decaimiento");
        dtoEntrada.setDiagnostico("Gastroenteritis");
        dtoEntrada.setTratamiento("Dieta blanda");
        dtoEntrada.setPeso(12.5);
        dtoEntrada.setTemperatura(38.5);

        FichaClinicaDTO dtoSalida = new FichaClinicaDTO(
                1L, 1L, 3L, 5L, LocalDate.of(2026, 6, 20),
                "Decaimiento", "Gastroenteritis", "Dieta blanda", null, 12.5, 38.5);
        when(fichaClinicaService.guardar(any(FichaClinicaCreateDTO.class))).thenReturn(dtoSalida);

        // When / Then: POST devuelve 201.
        mockMvc.perform(post("/api/v1/fichas-clinicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.diagnostico").value("Gastroenteritis"));
    }
}
