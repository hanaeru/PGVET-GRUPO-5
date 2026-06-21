package com.pgvet.ficha.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Datos para crear o actualizar una ficha clínica")
public class FichaClinicaCreateDTO {

    @Schema(description = "ID de la mascota atendida", example = "1")
    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;

    @Schema(description = "ID del veterinario que atiende", example = "3")
    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId;

    @Schema(description = "ID de la cita asociada", example = "5")
    @NotNull(message = "La cita es obligatoria")
    private Long citaId;

    @Schema(description = "Fecha de la atención clínica", example = "2026-06-20")
    @NotNull(message = "La fecha de atención es obligatoria")
    private LocalDate fechaAtencion;

    @Schema(description = "Síntomas reportados", example = "Decaimiento y falta de apetito")
    @NotBlank(message = "Los síntomas son obligatorios")
    private String sintomas;

    @Schema(description = "Diagnóstico clínico", example = "Gastroenteritis leve")
    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    @Schema(description = "Tratamiento indicado", example = "Dieta blanda por 5 días")
    @NotBlank(message = "El tratamiento es obligatorio")
    private String tratamiento;

    @Schema(description = "Observaciones adicionales", example = "Control en 7 días")
    private String observaciones;

    @Schema(description = "Peso registrado en kilogramos", example = "12.5")
    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor a 0")
    private Double peso;

    @Schema(description = "Temperatura corporal en grados Celsius", example = "38.5")
    @NotNull(message = "La temperatura es obligatoria")
    @Positive(message = "La temperatura debe ser mayor a 0")
    private Double temperatura;

    public FichaClinicaCreateDTO() {
    }

    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long mascotaId) { this.mascotaId = mascotaId; }

    public Long getVeterinarioId() { return veterinarioId; }
    public void setVeterinarioId(Long veterinarioId) { this.veterinarioId = veterinarioId; }

    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }

    public LocalDate getFechaAtencion() { return fechaAtencion; }
    public void setFechaAtencion(LocalDate fechaAtencion) { this.fechaAtencion = fechaAtencion; }

    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }
}
