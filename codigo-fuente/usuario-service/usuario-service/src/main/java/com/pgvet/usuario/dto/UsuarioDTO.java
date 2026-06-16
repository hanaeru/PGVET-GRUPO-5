package com.pgvet.usuario.dto;

// DTO de salida
// Este objeto se devuelve al cliente
public class UsuarioDTO {

    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private String comuna;
    private String region;
    private String rol;
    private String especialidad;
    private Boolean activo;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String rut, String nombre, String apellido, String correo,
                      String telefono, String direccion, String comuna, String region,
                      String rol, String especialidad, Boolean activo) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.comuna = comuna;
        this.region = region;
        this.rol = rol;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public String getRut() { return rut; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public String getComuna() { return comuna; }
    public String getRegion() { return region; }
    public String getRol() { return rol; }
    public String getEspecialidad() { return especialidad; }
    public Boolean getActivo() { return activo; }
}
