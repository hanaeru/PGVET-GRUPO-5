package com.pgvet.ficha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.ficha.model.FichaClinica;

@Repository
// Repository permite usar métodos listos para la base de datos
public interface FichaClinicaRepository extends JpaRepository<FichaClinica, Long> {

    // Busca fichas clínicas por mascota
    List<FichaClinica> findByMascotaId(Long mascotaId);

    // Busca fichas clínicas por veterinario
    List<FichaClinica> findByVeterinarioId(Long veterinarioId);

    // Busca fichas clínicas asociadas a una cita
    List<FichaClinica> findByCitaId(Long citaId);
    
}
