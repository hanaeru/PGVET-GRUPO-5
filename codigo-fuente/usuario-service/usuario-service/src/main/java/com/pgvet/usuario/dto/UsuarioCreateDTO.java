package com.pgvet.usuario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos para crear o actualizar un usuario del sistema")
public class UsuarioCreateDTO {

    @Schema(description = "RUT del usuario", example = "12345678-9")
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @Schema(description = "Nombre del usuario", example = "María")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "González")
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Schema(description = "Correo electrónico", example = "maria.gonzalez@correo.cl")
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene formato válido")
    private String correo;

    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Schema(description = "Dirección del domicilio", example = "Av. Principal 123")
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @Schema(description = "Comuna de residencia", example = "Santiago")
    @NotBlank(message = "La comuna es obligatoria")
    private String comuna;

    @Schema(description = "Región de residencia", example = "Metropolitana")
    @NotBlank(message = "La región es obligatoria")
    private String region;

    @Schema(description = "Rol en el sistema", example = "VETERINARIO")
    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    @Schema(description = "Especialidad veterinaria (solo para rol VETERINARIO)", example = "Cirugía")
    private String especialidad;

    public UsuarioCreateDTO() {
    }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}
