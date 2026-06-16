package com.pgvet.inventario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.inventario.model.ProductoInventario;

@Repository
public interface ProductoInventarioRepository extends JpaRepository<ProductoInventario, Long> {
}