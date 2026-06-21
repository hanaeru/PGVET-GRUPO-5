package com.pgvet.auth.repository;

import com.pgvet.auth.model.UsuarioAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Prueba el repositorio real contra H2 en memoria, sin MySQL ni Docker.
@DataJpaTest
class UsuarioAuthRepositoryTest {

    @Autowired
    private UsuarioAuthRepository repository;

    private UsuarioAuth crearCredencial(String correo) {
        UsuarioAuth usuario = new UsuarioAuth();
        usuario.setCorreo(correo);
        usuario.setPassword("clave123");
        usuario.setRol("TUTOR");
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDateTime.of(2026, 6, 20, 10, 0));
        return usuario;
    }

    @Test
    @DisplayName("save debe persistir una credencial en H2")
    void saveDebePersistirCredencial() {
        // Given: una credencial nueva sin ID asignado.
        UsuarioAuth credencial = crearCredencial("save@correo.cl");

        // When: guardamos con el repositorio JPA.
        UsuarioAuth guardada = repository.save(credencial);

        // Then: Hibernate asigna un ID y conserva el correo.
        assertTrue(guardada.getId() > 0);
        assertEquals("save@correo.cl", guardada.getCorreo());
    }

    @Test
    @DisplayName("findById debe recuperar la credencial guardada")
    void findByIdDebeRecuperarCredencial() {
        // Given: una credencial ya persistida.
        UsuarioAuth guardada = repository.save(crearCredencial("buscar@correo.cl"));

        // When: buscamos por el ID generado.
        Optional<UsuarioAuth> resultado = repository.findById(guardada.getId());

        // Then: debe existir y traer el mismo correo.
        assertTrue(resultado.isPresent());
        assertEquals("buscar@correo.cl", resultado.get().getCorreo());
    }

    @Test
    @DisplayName("findAll debe listar todas las credenciales")
    void findAllDebeListarCredenciales() {
        // Given: al menos una credencial en la base H2 de test.
        repository.save(crearCredencial("lista@correo.cl"));

        // When: pedimos el listado completo.
        List<UsuarioAuth> resultado = repository.findAll();

        // Then: debe haber al menos un registro.
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("existsByCorreo debe detectar si el correo ya está registrado")
    void existsByCorreoDebeDetectarCorreo() {
        // Given: un correo ya guardado en la base.
        repository.save(crearCredencial("existe@correo.cl"));

        // When / Then: existsByCorreo responde true para ese correo y false para otro.
        assertTrue(repository.existsByCorreo("existe@correo.cl"));
        assertFalse(repository.existsByCorreo("noexiste@correo.cl"));
    }
}
