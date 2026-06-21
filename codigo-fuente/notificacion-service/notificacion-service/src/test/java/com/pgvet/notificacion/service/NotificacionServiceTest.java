package com.pgvet.notificacion.service;

import com.pgvet.notificacion.client.UsuarioClient;
import com.pgvet.notificacion.dto.NotificacionCreateDTO;
import com.pgvet.notificacion.dto.NotificacionDTO;
import com.pgvet.notificacion.dto.UsuarioDTO;
import com.pgvet.notificacion.model.Notificacion;
import com.pgvet.notificacion.repository.NotificacionRepository;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private NotificacionService notificacionService;

    private NotificacionCreateDTO crearDtoValido() {
        NotificacionCreateDTO dto = new NotificacionCreateDTO();
        dto.setUsuarioId(1L);
        dto.setTitulo("Recordatorio de cita");
        dto.setMensaje("Su cita está programada para mañana a las 10:30");
        dto.setTipo("CITA");
        dto.setFechaEnvio(LocalDateTime.of(2026, 6, 16, 10, 0));
        dto.setLeido(false);
        return dto;
    }

    private Notificacion crearNotificacionEntidad() {
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuarioId(1L);
        notificacion.setTitulo("Recordatorio de cita");
        notificacion.setMensaje("Su cita está programada para mañana a las 10:30");
        notificacion.setTipo("CITA");
        notificacion.setFechaEnvio(LocalDateTime.of(2026, 6, 16, 10, 0));
        notificacion.setLeido(false);
        return notificacion;
    }

    private Request peticionFeign() {
        return Request.create(
                Request.HttpMethod.GET,
                "http://usuario-service/api/v1/usuarios/1",
                Map.of(),
                null,
                new RequestTemplate());
    }

    @Test
    @DisplayName("Debe crear notificación cuando el destinatario existe")
    void debeCrearNotificacionCuandoUsuarioExiste() {
        // Given: el usuario destinatario existe y el repositorio guarda la notificación.
        NotificacionCreateDTO dto = crearDtoValido();
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(1L);

        when(usuarioClient.buscarPorId(1L)).thenReturn(usuario);
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(crearNotificacionEntidad());

        // When: guardamos la notificación.
        NotificacionDTO resultado = notificacionService.guardar(dto);

        // Then: el mensaje y título deben coincidir con los datos enviados.
        assertNotNull(resultado);
        assertEquals("Recordatorio de cita", resultado.getTitulo());
        assertEquals("Su cita está programada para mañana a las 10:30", resultado.getMensaje());
        assertEquals(1L, resultado.getUsuarioId());
        verify(usuarioClient).buscarPorId(1L);
        verify(notificacionRepository).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Debe rechazar notificación cuando el usuario no existe")
    void debeRechazarNotificacionCuandoUsuarioNoExiste() {
        // Given: Usuario-Service responde 404 para el destinatario.
        NotificacionCreateDTO dto = crearDtoValido();
        when(usuarioClient.buscarPorId(1L))
                .thenThrow(new FeignException.NotFound("Not Found", peticionFeign(), null, Map.of()));

        // When / Then: no se debe guardar la notificación.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> notificacionService.guardar(dto));

        assertEquals("Usuario no encontrado", error.getMessage());
        verify(notificacionRepository, never()).save(any(Notificacion.class));
    }

    @Test
    @DisplayName("Debe retornar notificación cuando el ID existe")
    void debeRetornarNotificacionCuandoIdExiste() {
        // Given: el repositorio encuentra la notificación (usamos save simulado previo).
        Notificacion notificacion = crearNotificacionEntidad();
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));

        // When: buscamos por ID.
        Optional<NotificacionDTO> resultado = notificacionService.buscarPorId(1L);

        // Then: debe existir con el título esperado.
        assertTrue(resultado.isPresent());
        assertEquals("Recordatorio de cita", resultado.get().getTitulo());
    }

    @Test
    @DisplayName("Debe retornar vacío cuando la notificación no existe")
    void debeRetornarVacioCuandoNotificacionNoExiste() {
        // Given: no hay notificación con ese ID.
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        // When: buscamos un ID inexistente.
        Optional<NotificacionDTO> resultado = notificacionService.buscarPorId(99L);

        // Then: el Optional debe venir vacío.
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Debe listar todas las notificaciones")
    void debeListarNotificaciones() {
        // Given: el repositorio devuelve una notificación de prueba.
        when(notificacionRepository.findAll()).thenReturn(List.of(crearNotificacionEntidad()));

        // When: pedimos el listado.
        List<NotificacionDTO> resultado = notificacionService.listar();

        // Then: debe haber un elemento con tipo CITA.
        assertEquals(1, resultado.size());
        assertEquals("CITA", resultado.get(0).getTipo());
    }

    @Test
    @DisplayName("Debe lanzar error al eliminar notificación inexistente")
    void debeLanzarErrorAlEliminarNotificacionInexistente() {
        // Given: el ID a eliminar no existe.
        when(notificacionRepository.findById(50L)).thenReturn(Optional.empty());

        // When / Then: eliminar debe fallar con el mensaje del servicio.
        RuntimeException error = assertThrows(RuntimeException.class,
                () -> notificacionService.eliminar(50L));

        assertEquals("Notificación no encontrada", error.getMessage());
    }
}
