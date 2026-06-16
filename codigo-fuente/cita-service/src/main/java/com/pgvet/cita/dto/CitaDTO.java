package com.pgvet.cita.dto;

import java.time.LocalDate;
import java.time.LocalTime;

// DTO de salida
// Este objeto se devuelve al cliente
public class CitaDTO {

    private Long id;
    private Long mascotaId;
    private Long tutorId;
    private Long veterinarioId;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String estado;
    private String observacion;

    public CitaDTO() {
    }

    public CitaDTO(Long id, Long mascotaId, Long tutorId, Long veterinarioId,
                   LocalDate fecha, LocalTime hora, String motivo,
                   String estado, String observacion) {
        this.id = id;
        this.mascotaId = mascotaId;
        this.tutorId = tutorId;
        this.veterinarioId = veterinarioId;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.observacion = observacion;
    }

    public Long getId() { return id; }
    public Long getMascotaId() { return mascotaId; }
    public Long getTutorId() { return tutorId; }
    public Long getVeterinarioId() { return veterinarioId; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public String getMotivo() { return motivo; }
    public String getEstado() { return estado; }
    public String getObservacion() { return observacion; }
}
