package com.pgvet.usuario.service;

import com.pgvet.usuario.dto.UsuarioCreateDTO;
import com.pgvet.usuario.dto.UsuarioDTO;
import com.pgvet.usuario.model.Usuario;
import com.pgvet.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioCreateDTO crearDtoValido() {
        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setRut("12345678-9");
        dto.setNombre("María");
        dto.setApellido("González");
        dto.setCorreo("maria@correo.cl");
        dto.setTelefono("+56912345678");
        dto.setDireccion("Av. Principal 123");
        dto.setComuna("Santiago");
        dto.setRegion("Metropolitana");
        dto.setRol("TUTOR");
        return dto;
    }

    private Usuario crearUsuarioEntidad() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRut("12345678-9");
        usuario.setNombre("María");
        usuario.setApellido("González");
        usuario.setCorreo("maria@correo.cl");
        usuario.setTelefono("+56912345678");
        usuario.setDireccion("Av. Principal 123");
        usuario.setComuna("Santiago");
        usuario.setRegion("Metropolitana");
        usuario.setRol("TUTOR");
        usuario.setActivo(true);
        return usuario;
    }

    @Test
    @DisplayName("Debe crear usuario cuando el RUT tiene formato válido")
    void debeCrearUsuarioConRutValido() {
        // Given: DTO con RUT chileno válido y repositorio que simula el guardado.
        UsuarioCreateDTO dto = crearDtoValido();
        Usuario usuarioGuardado = crearUsuarioEntidad();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        // When: guardamos el usuario a través del servicio.
        UsuarioDTO resultado = usuarioService.guardar(dto);

        // Then: el DTO de salida debe reflejar los datos guardados.
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals("María", resultado.getNombre());
        assertTrue(resultado.getActivo());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe rechazar RUT con formato inválido")
    void debeRechazarRutInvalido() {
        // Given: un DTO con RUT que no cumple el patrón 12345678-9.
        UsuarioCreateDTO dto = crearDtoValido();
        dto.setRut("123456789");

        // When / Then: el servicio debe lanzar error antes de llamar al repositorio.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> usuarioService.guardar(dto));

        assertEquals("RUT con formato inválido. Use 12345678-9", error.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe retornar usuario cuando el ID existe")
    void debeRetornarUsuarioCuandoIdExiste() {
        // Given: el repositorio encuentra el usuario con ID 1.
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(crearUsuarioEntidad()));

        // When: buscamos por ID.
        Optional<UsuarioDTO> resultado = usuarioService.buscarPorId(1L);

        // Then: debe existir y traer el correo esperado.
        assertTrue(resultado.isPresent());
        assertEquals("maria@correo.cl", resultado.get().getCorreo());
    }

    @Test
    @DisplayName("Debe retornar vacío cuando el usuario no existe")
    void debeRetornarVacioCuandoUsuarioNoExiste() {
        // Given: no hay usuario con ese ID.
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // When: buscamos un ID inexistente.
        Optional<UsuarioDTO> resultado = usuarioService.buscarPorId(99L);

        // Then: el Optional debe venir vacío.
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe listar todos los usuarios")
    void debeListarUsuarios() {
        // Given: el repositorio devuelve un usuario de prueba.
        when(usuarioRepository.findAll()).thenReturn(List.of(crearUsuarioEntidad()));

        // When: pedimos el listado.
        List<UsuarioDTO> resultado = usuarioService.listar();

        // Then: debe haber un elemento con el RUT esperado.
        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRut());
    }

    @Test
    @DisplayName("Debe lanzar error al actualizar usuario inexistente")
    void debeLanzarErrorAlActualizarUsuarioInexistente() {
        // Given: el ID no existe en el repositorio simulado.
        when(usuarioRepository.findById(50L)).thenReturn(Optional.empty());
        UsuarioCreateDTO dto = crearDtoValido();

        // When / Then: actualizar debe fallar con el mensaje del servicio.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> usuarioService.actualizar(50L, dto));

        assertEquals("Usuario no encontrado", error.getMessage());
    }

    @Test
    @DisplayName("Debe eliminar usuario cuando el ID existe")
    void debeEliminarUsuarioExistente() {
        // Given: existe un usuario con ID 1.
        Usuario usuario = crearUsuarioEntidad();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // When: eliminamos por ID.
        usuarioService.eliminar(1L);

        // Then: el repositorio debe recibir la orden de borrar esa entidad.
        verify(usuarioRepository).delete(usuario);
    }
}
