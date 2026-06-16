package com.pgvet.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Esta clase representa la tabla usuarios_auth en la base de datos
@Entity
@Table(name = "usuarios_auth")
public class UsuarioAuth {

    // ID único de cada usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Correo del usuario para iniciar sesión
    private String correo;

    // Contraseña del usuario
    private String password;

    // Rol del usuario: ADMIN, VETERINARIO, RECEPCIONISTA o TUTOR
    private String rol;

    // Indica si el usuario está activo
    private Boolean activo;

    // Fecha en que se creó el usuario
    private LocalDateTime fechaCreacion;

    // Constructor vacío obligatorio para JPA
    public UsuarioAuth() {
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
