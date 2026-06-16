package com.pgvet.ficha.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// DTO de entrada
// Este objeto recibe el JSON para crear o actualizar fichas clínicas
public class FichaClinicaCreateDTO {

    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;

    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId;

    @NotNull(message = "La cita es obligatoria")
    private Long citaId;

    @NotNull(message = "La fecha de atención es obligatoria")
    private LocalDate fechaAtencion;

    @NotBlank(message = "Los síntomas son obligatorios")
    private String sintomas;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    @NotBlank(message = "El tratamiento es obligatorio")
    private String tratamiento;

    private String observaciones;

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor a 0")
    private Double peso;

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
