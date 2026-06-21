package com.pgvet.usuario.controller;

import com.pgvet.usuario.dto.UsuarioDTO;
import com.pgvet.usuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} debe responder 200 cuando el usuario existe")
    void getPorIdDebeResponder200CuandoExiste() throws Exception {
        // Given: el servicio devuelve un usuario encontrado.
        UsuarioDTO dto = new UsuarioDTO(
                1L, "12345678-9", "María", "González", "maria@correo.cl",
                "+56912345678", "Av. Principal 123", "Santiago", "Metropolitana",
                "TUTOR", null, true);
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(dto));

        // When / Then: la respuesta HTTP debe ser 200 con datos del usuario.
        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("María"))
                .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} debe responder 404 cuando el usuario no existe")
    void getPorIdDebeResponder404CuandoNoExiste() throws Exception {
        // Given: el servicio no encuentra el usuario.
        when(usuarioService.buscarPorId(99L)).thenReturn(Optional.empty());

        // When / Then: el endpoint debe responder 404 con el mensaje del controller.
        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    @DisplayName("POST /api/v1/usuarios debe responder 201 al crear usuario")
    void postDebeResponder201() throws Exception {
        // Given: JSON válido y servicio que simula el guardado.
        String json = """
                {
                  "rut": "12345678-9",
                  "nombre": "María",
                  "apellido": "González",
                  "correo": "maria@correo.cl",
                  "telefono": "+56912345678",
                  "direccion": "Av. Principal 123",
                  "comuna": "Santiago",
                  "region": "Metropolitana",
                  "rol": "TUTOR"
                }
                """;
        UsuarioDTO dto = new UsuarioDTO(
                1L, "12345678-9", "María", "González", "maria@correo.cl",
                "+56912345678", "Av. Principal 123", "Santiago", "Metropolitana",
                "TUTOR", null, true);
        when(usuarioService.guardar(org.mockito.ArgumentMatchers.any())).thenReturn(dto);

        // When / Then: POST devuelve 201.
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/v1/usuarios")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("María"));
    }
}
