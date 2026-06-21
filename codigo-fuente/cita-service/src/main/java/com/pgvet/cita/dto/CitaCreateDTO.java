package com.pgvet.cita.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos para crear o actualizar una cita veterinaria")
public class CitaCreateDTO {

    @Schema(description = "ID de la mascota", example = "1")
    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;

    @Schema(description = "ID del tutor (usuario)", example = "2")
    @NotNull(message = "El tutor es obligatorio")
    private Long tutorId;

    @Schema(description = "ID del veterinario (usuario)", example = "3")
    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId;

    @Schema(description = "Fecha de la cita", example = "2026-06-20")
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @Schema(description = "Hora de la cita", example = "10:30:00")
    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @Schema(description = "Motivo de la consulta", example = "Control anual de salud")
    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @Schema(description = "Estado de la cita", example = "PENDIENTE")
    private String estado;

    @Schema(description = "Observaciones adicionales", example = "Traer cartilla de vacunas")
    private String observacion;

    public CitaCreateDTO() {
    }

    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long mascotaId) { this.mascotaId = mascotaId; }

    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }

    public Long getVeterinarioId() { return veterinarioId; }
    public void setVeterinarioId(Long veterinarioId) { this.veterinarioId = veterinarioId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
