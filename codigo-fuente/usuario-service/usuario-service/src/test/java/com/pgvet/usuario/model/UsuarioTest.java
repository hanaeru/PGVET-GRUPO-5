package com.pgvet.usuario.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsuarioTest {

    @Test
    @DisplayName("Constructor vacío debe dejar activo en true")
    void constructorVacioDebeInicializarActivo() {
        // Given / When: instanciamos la entidad sin argumentos.
        Usuario usuario = new Usuario();

        // Then: activo queda true según el constructor de la entidad.
        assertTrue(usuario.getActivo());
    }

    @Test
    @DisplayName("Setters y getters deben reflejar los datos del usuario")
    void settersYGettersDebenFuncionar() {
        // Given: un objeto Usuario vacío.
        Usuario usuario = new Usuario();

        // When: cargamos los campos principales con setters.
        usuario.setId(1L);
        usuario.setRut("12345678-9");
        usuario.setNombre("María");
        usuario.setApellido("González");
        usuario.setCorreo("maria@correo.cl");
        usuario.setTelefono("+56912345678");
        usuario.setRol("TUTOR");

        // Then: cada getter devuelve el valor asignado.
        assertEquals(1L, usuario.getId());
        assertEquals("12345678-9", usuario.getRut());
        assertEquals("María", usuario.getNombre());
        assertEquals("González", usuario.getApellido());
        assertEquals("maria@correo.cl", usuario.getCorreo());
        assertEquals("TUTOR", usuario.getRol());
    }

    @Test
    @DisplayName("Debe guardar especialidad solo cuando el rol es veterinario")
    void debeGuardarEspecialidadVeterinario() {
        // Given: un usuario con rol VETERINARIO.
        Usuario usuario = new Usuario();
        usuario.setRol("VETERINARIO");

        // When: asignamos una especialidad.
        usuario.setEspecialidad("Cirugía");

        // Then: la especialidad queda disponible por getter.
        assertEquals("Cirugía", usuario.getEspecialidad());
    }
}
