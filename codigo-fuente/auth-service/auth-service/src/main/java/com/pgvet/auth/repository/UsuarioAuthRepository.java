package com.pgvet.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.auth.model.UsuarioAuth;

@Repository
// Repository permite usar métodos listos para la base de datos:
// save, findAll, findById, deleteById, etc.
public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, Long> {

    // Sirve para verificar si un correo ya está registrado
    boolean existsByCorreo(String correo);
}
