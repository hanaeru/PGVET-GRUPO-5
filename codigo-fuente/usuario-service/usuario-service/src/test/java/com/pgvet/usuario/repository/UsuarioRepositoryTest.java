package com.pgvet.usuario.repository;

import com.pgvet.usuario.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    private Usuario crearUsuario(String rut, String rol) {
        Usuario usuario = new Usuario();
        usuario.setRut(rut);
        usuario.setNombre("María");
        usuario.setApellido("González");
        usuario.setCorreo(rut.replace("-", "") + "@correo.cl");
        usuario.setTelefono("+56912345678");
        usuario.setDireccion("Av. Principal 123");
        usuario.setComuna("Santiago");
        usuario.setRegion("Metropolitana");
        usuario.setRol(rol);
        usuario.setActivo(true);
        return usuario;
    }

    @Test
    @DisplayName("save debe persistir un usuario")
    void saveDebePersistirUsuario() {
        // Given: un usuario nuevo sin ID.
        Usuario usuario = crearUsuario("12345678-9", "TUTOR");

        // When: lo guardamos en H2.
        Usuario guardado = repository.save(usuario);

        // Then: recibe ID y conserva el RUT.
        assertTrue(guardado.getId() > 0);
        assertEquals("12345678-9", guardado.getRut());
    }

    @Test
    @DisplayName("findById debe recuperar el usuario guardado")
    void findByIdDebeRecuperarUsuario() {
        // Given: un usuario persistido.
        Usuario guardado = repository.save(crearUsuario("87654321-0", "TUTOR"));

        // When: buscamos por ID.
        Optional<Usuario> resultado = repository.findById(guardado.getId());

        // Then: debe existir con el mismo RUT.
        assertTrue(resultado.isPresent());
        assertEquals("87654321-0", resultado.get().getRut());
    }

    @Test
    @DisplayName("findAll debe devolver la lista de usuarios")
    void findAllDebeListarUsuarios() {
        // Given: al menos un usuario en la base de test.
        repository.save(crearUsuario("11111111-1", "TUTOR"));

        // When: listamos todos.
        List<Usuario> resultado = repository.findAll();

        // Then: la lista no está vacía.
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("findByRol debe filtrar usuarios por rol")
    void findByRolDebeFiltrarPorRol() {
        // Given: un veterinario y un tutor en la base.
        repository.save(crearUsuario("22222222-2", "VETERINARIO"));
        repository.save(crearUsuario("33333333-3", "TUTOR"));

        // When: buscamos solo veterinarios.
        List<Usuario> veterinarios = repository.findByRol("VETERINARIO");

        // Then: todos los resultados tienen rol VETERINARIO.
        assertFalse(veterinarios.isEmpty());
        assertEquals("VETERINARIO", veterinarios.get(0).getRol());
    }
}
