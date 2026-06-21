package com.pgvet.notificacion.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class NotificacionTest {

    @Test
    @DisplayName("Constructor vacío debe crear instancia usable")
    void constructorVacioDebeCrearInstancia() {
        // Given / When: instanciamos la entidad sin argumentos.
        Notificacion notificacion = new Notificacion();

        // When: asignamos campos mínimos.
        notificacion.setTitulo("Aviso");
        notificacion.setLeido(false);

        // Then: los valores quedan accesibles por getter.
        assertEquals("Aviso", notificacion.getTitulo());
        assertFalse(notificacion.getLeido());
    }

    @Test
    @DisplayName("Setters y getters deben reflejar los datos de la notificación")
    void settersYGettersDebenFuncionar() {
        // Given: una notificación vacía.
        Notificacion notificacion = new Notificacion();
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 20, 10, 0);

        // When: cargamos todos los campos disponibles.
        notificacion.setUsuarioId(1L);
        notificacion.setTitulo("Recordatorio");
        notificacion.setMensaje("Su cita es mañana");
        notificacion.setTipo("CITA");
        notificacion.setFechaEnvio(fecha);
        notificacion.setLeido(false);

        // Then: los getters devuelven lo guardado.
        assertEquals(1L, notificacion.getUsuarioId());
        assertEquals("Recordatorio", notificacion.getTitulo());
        assertEquals("CITA", notificacion.getTipo());
        assertEquals(fecha, notificacion.getFechaEnvio());
    }

    @Test
    @DisplayName("Debe permitir marcar la notificación como leída")
    void debePermitirMarcarComoLeida() {
        // Given: notificación no leída.
        Notificacion notificacion = new Notificacion();
        notificacion.setLeido(false);

        // When: cambiamos el estado a leída.
        notificacion.setLeido(true);

        // Then: el getter refleja el cambio.
        assertEquals(true, notificacion.getLeido());
    }
}
