package com.pgvet.cita.repository;

import com.pgvet.cita.model.Cita;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CitaRepositoryTest {

    @Autowired
    private CitaRepository repository;

    private Cita crearCita(Long veterinarioId, LocalDate fecha, LocalTime hora) {
        Cita cita = new Cita();
        cita.setMascotaId(1L);
        cita.setTutorId(2L);
        cita.setVeterinarioId(veterinarioId);
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setMotivo("Consulta");
        cita.setEstado("AGENDADA");
        return cita;
    }

    @Test
    @DisplayName("save debe persistir una cita")
    void saveDebePersistirCita() {
        // Given: una cita sin ID.
        Cita cita = crearCita(3L, LocalDate.of(2026, 6, 20), LocalTime.of(10, 0));

        // When: guardamos en H2.
        Cita guardada = repository.save(cita);

        // Then: recibe ID y mantiene el motivo.
        assertTrue(guardada.getId() > 0);
        assertEquals("Consulta", guardada.getMotivo());
    }

    @Test
    @DisplayName("findById debe recuperar la cita guardada")
    void findByIdDebeRecuperarCita() {
        // Given: cita persistida.
        Cita guardada = repository.save(
                crearCita(4L, LocalDate.of(2026, 6, 21), LocalTime.of(11, 0)));

        // When: buscamos por ID.
        Optional<Cita> resultado = repository.findById(guardada.getId());

        // Then: existe con el veterinario esperado.
        assertTrue(resultado.isPresent());
        assertEquals(4L, resultado.get().getVeterinarioId());
    }

    @Test
    @DisplayName("findAll debe listar citas")
    void findAllDebeListarCitas() {
        // Given: al menos una cita en la base.
        repository.save(crearCita(5L, LocalDate.of(2026, 6, 22), LocalTime.of(9, 0)));

        // When: listamos.
        List<Cita> resultado = repository.findAll();

        // Then: hay registros.
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("findByEstado debe filtrar citas por estado")
    void findByEstadoDebeFiltrarPorEstado() {
        // Given: una cita en estado AGENDADA.
        repository.save(crearCita(6L, LocalDate.of(2026, 6, 23), LocalTime.of(14, 0)));

        // When: filtramos por AGENDADA.
        List<Cita> resultado = repository.findByEstado("AGENDADA");

        // Then: todas tienen ese estado.
        assertFalse(resultado.isEmpty());
        assertEquals("AGENDADA", resultado.get(0).getEstado());
    }
}
