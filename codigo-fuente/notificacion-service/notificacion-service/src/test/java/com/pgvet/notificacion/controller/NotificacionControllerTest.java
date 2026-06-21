package com.pgvet.notificacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pgvet.notificacion.dto.NotificacionCreateDTO;
import com.pgvet.notificacion.dto.NotificacionDTO;
import com.pgvet.notificacion.service.NotificacionService;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NotificacionControllerTest {

    @Mock
    private NotificacionService notificacionService;

    @InjectMocks
    private NotificacionController notificacionController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificacionController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("GET /api/v1/notificaciones/{id} debe responder 200 cuando existe")
    void getPorIdDebeResponder200() throws Exception {
        // Given: el servicio encuentra la notificación.
        NotificacionDTO dto = new NotificacionDTO(
                1L, 1L, "Recordatorio", "Su cita es mañana", "CITA",
                LocalDateTime.of(2026, 6, 20, 10, 0), false);
        when(notificacionService.buscarPorId(1L)).thenReturn(Optional.of(dto));

        // When / Then: GET devuelve 200 con el título.
        mockMvc.perform(get("/api/v1/notificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Recordatorio"));
    }

    @Test
    @DisplayName("GET /api/v1/notificaciones/{id} debe responder 404 cuando no existe")
    void getPorIdDebeResponder404() throws Exception {
        // Given: el servicio no encuentra la notificación.
        when(notificacionService.buscarPorId(99L)).thenReturn(Optional.empty());

        // When / Then: responde 404 con el mensaje del controller.
        mockMvc.perform(get("/api/v1/notificaciones/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Notificación no encontrada"));
    }

    @Test
    @DisplayName("POST /api/v1/notificaciones debe responder 201 al crear")
    void postDebeResponder201() throws Exception {
        // Given: DTO válido y servicio simulado.
        NotificacionCreateDTO dtoEntrada = new NotificacionCreateDTO();
        dtoEntrada.setUsuarioId(1L);
        dtoEntrada.setTitulo("Recordatorio");
        dtoEntrada.setMensaje("Su cita es mañana");
        dtoEntrada.setTipo("CITA");
        dtoEntrada.setFechaEnvio(LocalDateTime.of(2026, 6, 20, 10, 0));
        dtoEntrada.setLeido(false);

        NotificacionDTO dtoSalida = new NotificacionDTO(
                1L, 1L, "Recordatorio", "Su cita es mañana", "CITA",
                LocalDateTime.of(2026, 6, 20, 10, 0), false);
        when(notificacionService.guardar(any(NotificacionCreateDTO.class))).thenReturn(dtoSalida);

        // When / Then: POST devuelve 201.
        mockMvc.perform(post("/api/v1/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipo").value("CITA"));
    }
}
