package com.pgvet.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.pago.model.Pago;

@Repository
// JpaRepository entrega CRUD automático
public interface PagoRepository extends JpaRepository<Pago, Long> {
}
