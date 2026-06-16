package com.pgvet.usuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgvet.usuario.model.Usuario;

@Repository
// Repository permite usar métodos listos para la base de datos
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Verifica si ya existe un RUT registrado
    boolean existsByRut(String rut);

    // Verifica si ya existe un correo registrado
    boolean existsByCorreo(String correo);

    // Busca usuarios según su rol
    List<Usuario> findByRol(String rol);
    
}
