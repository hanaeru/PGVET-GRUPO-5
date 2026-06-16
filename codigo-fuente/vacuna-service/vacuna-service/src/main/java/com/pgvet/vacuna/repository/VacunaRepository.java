package com.pgvet.vacuna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.vacuna.model.Vacuna;

@Repository
// JpaRepository entrega CRUD automático
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {

}