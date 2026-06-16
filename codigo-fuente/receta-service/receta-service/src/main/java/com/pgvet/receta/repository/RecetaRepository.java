package com.pgvet.receta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.receta.model.Receta;

@Repository
// JpaRepository entrega los métodos CRUD básicos
public interface RecetaRepository extends JpaRepository<Receta, Long> {
}
