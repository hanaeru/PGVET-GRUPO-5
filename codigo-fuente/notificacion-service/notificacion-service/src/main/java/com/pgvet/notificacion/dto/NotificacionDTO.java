package com.pgvet.notificacion.dto;

import java.time.LocalDateTime;

// DTO de salida
// Este objeto se devuelve al cliente
public class NotificacionDTO {

    private Long id;
    private Long usuarioId;
    private String titulo;
    private String mensaje;
    private String tipo;
    private LocalDateTime fechaEnvio;
    private Boolean leido;

    public NotificacionDTO() {
    }

    public NotificacionDTO(Long id, Long usuarioId, String titulo, String mensaje,
                           String tipo, LocalDateTime fechaEnvio, Boolean leido) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.fechaEnvio = fechaEnvio;
        this.leido = leido;
    }

    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public String getTipo() { return tipo; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public Boolean getLeido() { return leido; }
}
