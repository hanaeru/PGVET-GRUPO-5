package com.pgvet.cita.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pgvet.cita.dto.CitaCreateDTO;
import com.pgvet.cita.dto.CitaDTO;
import com.pgvet.cita.service.CitaService;
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
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CitaControllerTest {

    @Mock
    private CitaService citaService;

    @InjectMocks
    private CitaController citaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(citaController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("GET /api/v1/citas/{id} debe responder 200 cuando existe")
    void getPorIdDebeResponder200() throws Exception {
        // Given: el servicio devuelve una cita encontrada.
        CitaDTO dto = new CitaDTO(
                1L, 1L, 2L, 3L,
                LocalDate.of(2026, 6, 20), LocalTime.of(10, 30),
                "Control anual", "AGENDADA", null);
        when(citaService.buscarPorId(1L)).thenReturn(Optional.of(dto));

        // When / Then: GET responde 200 con el motivo en JSON.
        mockMvc.perform(get("/api/v1/citas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.motivo").value("Control anual"));
    }

    @Test
    @DisplayName("GET /api/v1/citas/{id} debe responder 404 cuando no existe")
    void getPorIdDebeResponder404() throws Exception {
        // Given: el servicio no encuentra la cita.
        when(citaService.buscarPorId(99L)).thenReturn(Optional.empty());

        // When / Then: el controller responde 404.
        mockMvc.perform(get("/api/v1/citas/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cita no encontrada"));
    }

    @Test
    @DisplayName("POST /api/v1/citas debe responder 201 al crear cita")
    void postDebeResponder201() throws Exception {
        // Given: DTO de entrada y respuesta simulada del servicio.
        CitaCreateDTO dtoEntrada = new CitaCreateDTO();
        dtoEntrada.setMascotaId(1L);
        dtoEntrada.setTutorId(2L);
        dtoEntrada.setVeterinarioId(3L);
        dtoEntrada.setFecha(LocalDate.of(2026, 6, 20));
        dtoEntrada.setHora(LocalTime.of(10, 30));
        dtoEntrada.setMotivo("Control anual");

        CitaDTO dtoSalida = new CitaDTO(
                1L, 1L, 2L, 3L,
                LocalDate.of(2026, 6, 20), LocalTime.of(10, 30),
                "Control anual", "AGENDADA", null);
        when(citaService.guardar(any(CitaCreateDTO.class))).thenReturn(dtoSalida);

        // When / Then: POST devuelve 201.
        mockMvc.perform(post("/api/v1/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("AGENDADA"));
    }
}
