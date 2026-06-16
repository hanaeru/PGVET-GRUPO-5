package com.pgvet.mascota.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.mascota.model.Mascota;

// Repository permite usar métodos listos para la base de datos
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // Verifica si ya existe una mascota con ese microchip
    boolean existsByMicrochip(String microchip);

    // Busca mascotas por tutor
    List<Mascota> findByTutorId(Long tutorId);

    // Busca mascotas por especie
    List<Mascota> findByEspecie(String especie);
}
