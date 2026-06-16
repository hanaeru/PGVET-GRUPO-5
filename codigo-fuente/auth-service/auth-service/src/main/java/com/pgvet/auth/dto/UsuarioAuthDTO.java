package com.pgvet.auth.dto;
import java.time.LocalDateTime;

// DTO de salida
// Este objeto se devuelve al cliente
// No incluye password
public class UsuarioAuthDTO {

    private Long id;
    private String correo;
    private String rol;
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    public UsuarioAuthDTO() {
    }

    public UsuarioAuthDTO(Long id, String correo, String rol, Boolean activo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.correo = correo;
        this.rol = rol;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
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
}
