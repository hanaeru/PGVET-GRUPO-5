package com.pgvet.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para crear o actualizar credenciales de autenticación")
public class UsuarioAuthCreateDTO {

    @Schema(description = "Correo electrónico del usuario", example = "tutor@correo.cl")
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene formato válido")
    private String correo;

    @Schema(description = "Contraseña de acceso", example = "clave123")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
    private String password;

    @Schema(description = "Rol del usuario en el sistema", example = "TUTOR")
    @NotBlank(message = "El rol es obligatorio")
    private String rol;

    public UsuarioAuthCreateDTO() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
