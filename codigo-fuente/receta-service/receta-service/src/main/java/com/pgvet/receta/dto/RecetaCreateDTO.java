package com.pgvet.receta.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO de entrada
// Este objeto recibe el JSON para crear o actualizar recetas
public class RecetaCreateDTO {

    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;

    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId;

    @NotNull(message = "La ficha clínica es obligatoria")
    private Long fichaClinicaId;

    @NotBlank(message = "El medicamento es obligatorio")
    private String medicamento;

    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;

    @NotBlank(message = "La frecuencia es obligatoria")
    private String frecuencia;

    @NotBlank(message = "La duración es obligatoria")
    private String duracion;

    @NotBlank(message = "Las indicaciones son obligatorias")
    private String indicaciones;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDate fechaEmision;

    public RecetaCreateDTO() {
    }

    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long mascotaId) { this.mascotaId = mascotaId; }

    public Long getVeterinarioId() { return veterinarioId; }
    public void setVeterinarioId(Long veterinarioId) { this.veterinarioId = veterinarioId; }

    public Long getFichaClinicaId() { return fichaClinicaId; }
    public void setFichaClinicaId(Long fichaClinicaId) { this.fichaClinicaId = fichaClinicaId; }

    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
}
