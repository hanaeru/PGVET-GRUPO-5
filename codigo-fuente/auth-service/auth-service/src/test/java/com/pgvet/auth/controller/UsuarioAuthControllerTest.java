package com.pgvet.auth.controller;

import com.pgvet.auth.dto.UsuarioAuthDTO;
import com.pgvet.auth.service.UsuarioAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// MockMvc en modo standalone: prueba el controller sin levantar Spring Boot completo.
@ExtendWith(MockitoExtension.class)
class UsuarioAuthControllerTest {

    @Mock
    private UsuarioAuthService usuarioAuthService;

    @InjectMocks
    private UsuarioAuthController usuarioAuthController;

    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        // Armamos MockMvc directamente con el controller y su servicio simulado.
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioAuthController).build();
    }

    @Test
    @DisplayName("GET /api/v1/auth/{id} debe responder 200 cuando la credencial existe")
    void getPorIdDebeResponder200CuandoExiste() throws Exception {
        // Given: el servicio simulado encuentra la credencial solicitada.
        UsuarioAuthDTO dto = new UsuarioAuthDTO(
                1L, "tutor@correo.cl", "TUTOR", true, LocalDateTime.of(2026, 6, 16, 10, 0));
        when(usuarioAuthService.buscarPorId(1L)).thenReturn(Optional.of(dto));

        // When / Then: la petición GET debe devolver 200 y el correo en JSON.
        mockMvc.perform(get("/api/v1/auth/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("tutor@correo.cl"))
                .andExpect(jsonPath("$.rol").value("TUTOR"));
    }

    @Test
    @DisplayName("GET /api/v1/auth/{id} debe responder 404 cuando la credencial no existe")
    void getPorIdDebeResponder404CuandoNoExiste() throws Exception {
        // Given: el servicio no encuentra el ID pedido.
        when(usuarioAuthService.buscarPorId(99L)).thenReturn(Optional.empty());

        // When / Then: el controller debe responder 404 con el mensaje definido.
        mockMvc.perform(get("/api/v1/auth/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario auth no encontrado"));
    }

    @Test
    @DisplayName("POST /api/v1/auth debe responder 201 al registrar credencial")
    void postDebeResponder201() throws Exception {
        // Given: JSON de registro y servicio que simula la creación.
        String json = """
                {
                  "correo": "nuevo@correo.cl",
                  "password": "clave123",
                  "rol": "TUTOR"
                }
                """;
        UsuarioAuthDTO dto = new UsuarioAuthDTO(
                1L, "nuevo@correo.cl", "TUTOR", true, LocalDateTime.of(2026, 6, 20, 10, 0));
        when(usuarioAuthService.guardar(org.mockito.ArgumentMatchers.any())).thenReturn(dto);

        // When / Then: POST devuelve 201 con el correo registrado.
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/auth")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.correo").value("nuevo@correo.cl"));
    }
}
