package com.pgvet.notificacion.repository;

import com.pgvet.notificacion.model.Notificacion;
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

@DataJpaTest
class NotificacionRepositoryTest {

    @Autowired
    private NotificacionRepository repository;

    private Notificacion crearNotificacion(String titulo) {
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuarioId(1L);
        notificacion.setTitulo(titulo);
        notificacion.setMensaje("Mensaje de prueba");
        notificacion.setTipo("CITA");
        notificacion.setFechaEnvio(LocalDateTime.of(2026, 6, 20, 10, 0));
        notificacion.setLeido(false);
        return notificacion;
    }

    @Test
    @DisplayName("save debe persistir una notificación")
    void saveDebePersistirNotificacion() {
        // Given: notificación sin ID.
        Notificacion notificacion = crearNotificacion("Recordatorio");

        // When: guardamos en H2.
        Notificacion guardada = repository.save(notificacion);

        // Then: recibe ID y conserva el título.
        assertTrue(guardada.getId() > 0);
        assertEquals("Recordatorio", guardada.getTitulo());
    }

    @Test
    @DisplayName("findById debe recuperar la notificación guardada")
    void findByIdDebeRecuperarNotificacion() {
        // Given: notificación persistida.
        Notificacion guardada = repository.save(crearNotificacion("Buscar"));

        // When: buscamos por ID.
        Optional<Notificacion> resultado = repository.findById(guardada.getId());

        // Then: existe con el título esperado.
        assertTrue(resultado.isPresent());
        assertEquals("Buscar", resultado.get().getTitulo());
    }

    @Test
    @DisplayName("findAll debe listar notificaciones")
    void findAllDebeListarNotificaciones() {
        // Given: al menos una notificación en la base.
        repository.save(crearNotificacion("Lista"));

        // When: listamos todas.
        List<Notificacion> resultado = repository.findAll();

        // Then: hay registros.
        assertFalse(resultado.isEmpty());
    }
}
