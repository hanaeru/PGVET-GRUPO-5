package com.pgvet.usuario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Esta clase representa la tabla usuarios en la base de datos
@Entity
@Table(name = "usuarios")
public class Usuario {

    // ID único del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RUT chileno del usuario
    private String rut;

    // Nombre del usuario
    private String nombre;

    // Apellido del usuario
    private String apellido;

    // Correo del usuario
    private String correo;

    // Teléfono de contacto
    private String telefono;

    // Dirección del usuario
    private String direccion;

    // Comuna del usuario
    private String comuna;

    // Región del usuario
    private String region;

    // Rol: ADMIN, VETERINARIO, RECEPCIONISTA o TUTOR
    private String rol;

    // Solo se usa si el usuario es veterinario
    private String especialidad;

    // Indica si el usuario está activo
    private Boolean activo;

    // Constructor vacío obligatorio para JPA
    public Usuario() {
        this.activo = true;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public String getRegion() {
        return region;
    }

    public String getRol() {
        return rol;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
