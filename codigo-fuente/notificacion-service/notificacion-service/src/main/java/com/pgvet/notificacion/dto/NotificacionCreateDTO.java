package com.pgvet.notificacion.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos para crear o actualizar una notificación")
public class NotificacionCreateDTO {

    @Schema(description = "ID del usuario destinatario", example = "1")
    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @Schema(description = "Título de la notificación", example = "Recordatorio de cita")
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Schema(description = "Contenido del mensaje", example = "Su cita está programada para mañana a las 10:30")
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @Schema(description = "Tipo de notificación", example = "CITA")
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @Schema(description = "Fecha y hora de envío", example = "2026-06-16T10:00:00")
    @NotNull(message = "La fecha de envío es obligatoria")
    private LocalDateTime fechaEnvio;

    @Schema(description = "Indica si la notificación fue leída", example = "false")
    @NotNull(message = "El estado leído es obligatorio")
    private Boolean leido;

    public NotificacionCreateDTO() {
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
}
