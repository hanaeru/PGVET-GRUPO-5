package com.pgvet.cita.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.cita.model.Cita;

@Repository
// Repository permite usar métodos listos para la base de datos
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Busca citas de una mascota
    List<Cita> findByMascotaId(Long mascotaId);

    // Busca citas de un tutor
    List<Cita> findByTutorId(Long tutorId);

    // Busca citas de un veterinario
    List<Cita> findByVeterinarioId(Long veterinarioId);

    // Busca citas por fecha
    List<Cita> findByFecha(LocalDate fecha);

    // Busca citas por estado
    List<Cita> findByEstado(String estado);

    // Revisa si ya existe cita para el mismo veterinario, fecha y hora
    boolean existsByVeterinarioIdAndFechaAndHora(Long veterinarioId, LocalDate fecha, LocalTime hora);

    // Igual que arriba pero ignorando una cita al actualizar
    boolean existsByVeterinarioIdAndFechaAndHoraAndIdNot(Long veterinarioId, LocalDate fecha,
                                                         LocalTime hora, Long id);

}
