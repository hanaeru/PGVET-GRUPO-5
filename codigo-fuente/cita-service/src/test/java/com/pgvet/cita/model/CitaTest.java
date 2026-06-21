package com.pgvet.cita.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CitaTest {

    @Test
    @DisplayName("Constructor vacío debe asignar estado AGENDADA")
    void constructorVacioDebeAsignarEstadoAgendada() {
        // Given / When: instanciamos una cita nueva.
        Cita cita = new Cita();

        // Then: el estado por defecto es AGENDADA según la entidad.
        assertEquals("AGENDADA", cita.getEstado());
    }

    @Test
    @DisplayName("Setters y getters deben reflejar los datos de la cita")
    void settersYGettersDebenFuncionar() {
        // Given: una cita vacía.
        Cita cita = new Cita();
        LocalDate fecha = LocalDate.of(2026, 6, 20);
        LocalTime hora = LocalTime.of(10, 30);

        // When: cargamos los campos con setters.
        cita.setId(1L);
        cita.setMascotaId(1L);
        cita.setTutorId(2L);
        cita.setVeterinarioId(3L);
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setMotivo("Control anual");
        cita.setEstado("CONFIRMADA");

        // Then: los getters devuelven los valores asignados.
        assertEquals(1L, cita.getMascotaId());
        assertEquals("Control anual", cita.getMotivo());
        assertEquals("CONFIRMADA", cita.getEstado());
        assertEquals(fecha, cita.getFecha());
    }

    @Test
    @DisplayName("Debe permitir guardar observación opcional")
    void debePermitirObservacion() {
        // Given: una cita sin observación.
        Cita cita = new Cita();

        // When: agregamos una observación.
        cita.setObservacion("Traer cartilla de vacunas");

        // Then: el getter devuelve el texto guardado.
        assertEquals("Traer cartilla de vacunas", cita.getObservacion());
    }
}
