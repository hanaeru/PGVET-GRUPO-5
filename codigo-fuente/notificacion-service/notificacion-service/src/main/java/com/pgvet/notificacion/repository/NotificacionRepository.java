package com.pgvet.notificacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.notificacion.model.Notificacion;

@Repository
// JpaRepository entrega CRUD automático
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}