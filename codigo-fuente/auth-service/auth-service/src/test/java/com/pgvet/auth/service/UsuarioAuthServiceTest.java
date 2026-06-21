package com.pgvet.auth.service;

import com.pgvet.auth.dto.UsuarioAuthCreateDTO;
import com.pgvet.auth.dto.UsuarioAuthDTO;
import com.pgvet.auth.model.UsuarioAuth;
import com.pgvet.auth.repository.UsuarioAuthRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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

// Pruebas unitarias del servicio de credenciales de auth.
// Usamos Mockito para simular el repositorio y no depender de MySQL.
@ExtendWith(MockitoExtension.class)
class UsuarioAuthServiceTest {

    @Mock
    private UsuarioAuthRepository usuarioAuthRepository;

    @InjectMocks
    private UsuarioAuthService usuarioAuthService;

    // Arma un DTO de entrada con datos válidos para registrar credenciales.
    private UsuarioAuthCreateDTO crearDtoRegistro() {
        UsuarioAuthCreateDTO dto = new UsuarioAuthCreateDTO();
        dto.setCorreo("tutor@correo.cl");
        dto.setPassword("clave123");
        dto.setRol("TUTOR");
        return dto;
    }

    // Arma una entidad guardada como si viniera de la base de datos.
    private UsuarioAuth crearUsuarioGuardado() {
        UsuarioAuth usuario = new UsuarioAuth();
        usuario.setId(1L);
        usuario.setCorreo("tutor@correo.cl");
        usuario.setPassword("clave123");
        usuario.setRol("TUTOR");
        usuario.setActivo(true);
        usuario.setFechaCreacion(LocalDateTime.of(2026, 6, 16, 10, 0));
        return usuario;
    }

    @Test
    @DisplayName("Debe registrar credenciales y devolver DTO sin password")
    void debeRegistrarCredencialesExitosamente() {
        // Given: preparamos un DTO de registro y simulamos que el repositorio guarda la entidad.
        UsuarioAuthCreateDTO dto = crearDtoRegistro();
        UsuarioAuth usuarioGuardado = crearUsuarioGuardado();

        when(usuarioAuthRepository.save(any(UsuarioAuth.class))).thenReturn(usuarioGuardado);

        // When: llamamos al método real que crea credenciales en auth-service.
        UsuarioAuthDTO resultado = usuarioAuthService.guardar(dto);

        // Then: el resultado no debe incluir password y debe traer correo y rol correctos.
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("tutor@correo.cl", resultado.getCorreo());
        assertEquals("TUTOR", resultado.getRol());
        assertTrue(resultado.getActivo());
        verify(usuarioAuthRepository).save(any(UsuarioAuth.class));
    }

    @Test
    @DisplayName("Debe retornar credencial cuando el ID existe")
    void debeRetornarCredencialCuandoIdExiste() {
        // Given: el repositorio simulado tiene una credencial con ID 1.
        UsuarioAuth usuario = crearUsuarioGuardado();
        when(usuarioAuthRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // When: buscamos por ID en el servicio.
        Optional<UsuarioAuthDTO> resultado = usuarioAuthService.buscarPorId(1L);

        // Then: debe existir y coincidir el correo registrado.
        assertTrue(resultado.isPresent());
        assertEquals("tutor@correo.cl", resultado.get().getCorreo());
    }

    @Test
    @DisplayName("Debe retornar vacío cuando el ID no existe")
    void debeRetornarVacioCuandoIdNoExiste() {
        // Given: el repositorio no encuentra el ID solicitado.
        when(usuarioAuthRepository.findById(99L)).thenReturn(Optional.empty());

        // When: buscamos una credencial inexistente.
        Optional<UsuarioAuthDTO> resultado = usuarioAuthService.buscarPorId(99L);

        // Then: el Optional debe venir vacío, sin lanzar excepción.
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe listar todas las credenciales registradas")
    void debeListarCredenciales() {
        // Given: el repositorio devuelve una lista con una credencial de prueba.
        when(usuarioAuthRepository.findAll()).thenReturn(List.of(crearUsuarioGuardado()));

        // When: pedimos el listado completo al servicio.
        List<UsuarioAuthDTO> resultado = usuarioAuthService.listar();

        // Then: debe haber un elemento con el correo esperado.
        assertEquals(1, resultado.size());
        assertEquals("tutor@correo.cl", resultado.get(0).getCorreo());
    }

    @Test
    @DisplayName("Debe lanzar error al actualizar credencial inexistente")
    void debeLanzarErrorAlActualizarCredencialInexistente() {
        // Given: no existe credencial con ID 50 en el repositorio simulado.
        when(usuarioAuthRepository.findById(50L)).thenReturn(Optional.empty());
        UsuarioAuthCreateDTO dto = crearDtoRegistro();

        // When / Then: actualizar debe fallar con el mensaje definido en el servicio.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> usuarioAuthService.actualizar(50L, dto));

        assertEquals("Usuario auth no encontrado", error.getMessage());
        verify(usuarioAuthRepository, never()).save(any(UsuarioAuth.class));
    }

    @Test
    @DisplayName("Debe lanzar error al eliminar credencial inexistente")
    void debeLanzarErrorAlEliminarCredencialInexistente() {
        // Given: el ID a eliminar no está en la base simulada.
        when(usuarioAuthRepository.findById(50L)).thenReturn(Optional.empty());

        // When / Then: eliminar debe lanzar la excepción de no encontrado.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> usuarioAuthService.eliminar(50L));

        assertEquals("Usuario auth no encontrado", error.getMessage());
        verify(usuarioAuthRepository, never()).delete(any(UsuarioAuth.class));
    }
}
