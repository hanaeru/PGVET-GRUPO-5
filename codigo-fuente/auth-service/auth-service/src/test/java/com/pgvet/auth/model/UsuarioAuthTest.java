package com.pgvet.auth.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioAuthTest {

    @Test
    @DisplayName("Constructor vacío debe inicializar activo en true")
    void constructorVacioDebeInicializarActivo() {
        // Given / When: creamos la entidad con el constructor sin argumentos.
        UsuarioAuth usuario = new UsuarioAuth();

        // Then: el campo activo viene activado por defecto, como define la entidad.
        assertTrue(usuario.getActivo());
        assertNotNull(usuario.getFechaCreacion());
    }

    @Test
    @DisplayName("Setters y getters deben guardar y devolver los valores asignados")
    void settersYGettersDebenFuncionar() {
        // Given: preparamos fechas y textos de prueba.
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 20, 9, 0);
        UsuarioAuth usuario = new UsuarioAuth();

        // When: asignamos cada campo con su setter.
        usuario.setId(1L);
        usuario.setCorreo("tutor@correo.cl");
        usuario.setPassword("clave123");
        usuario.setRol("TUTOR");
        usuario.setActivo(false);
        usuario.setFechaCreacion(fecha);

        // Then: los getters deben devolver exactamente lo que guardamos.
        assertEquals(1L, usuario.getId());
        assertEquals("tutor@correo.cl", usuario.getCorreo());
        assertEquals("clave123", usuario.getPassword());
        assertEquals("TUTOR", usuario.getRol());
        assertEquals(false, usuario.getActivo());
        assertEquals(fecha, usuario.getFechaCreacion());
    }

    @Test
    @DisplayName("Debe permitir cambiar el rol después de crear la entidad")
    void debePermitirCambiarRol() {
        // Given: una credencial creada con rol TUTOR.
        UsuarioAuth usuario = new UsuarioAuth();
        usuario.setRol("TUTOR");

        // When: actualizamos el rol a VETERINARIO.
        usuario.setRol("VETERINARIO");

        // Then: el getter refleja el nuevo valor.
        assertEquals("VETERINARIO", usuario.getRol());
    }
}
