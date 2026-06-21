package com.pgvet.receta.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO de entrada
// Este objeto recibe el JSON para crear o actualizar recetas
@Schema(description = "Datos para crear o actualizar una receta médica")
public class RecetaCreateDTO {

    @Schema(description = "ID de la mascota tratada", example = "1")
    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;

    @Schema(description = "ID del veterinario que emite la receta", example = "2")
    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId;

    @Schema(description = "ID de la ficha clínica asociada", example = "5")
    @NotNull(message = "La ficha clínica es obligatoria")
    private Long fichaClinicaId;

    @Schema(description = "Nombre del medicamento prescrito", example = "Amoxicilina 250mg")
    @NotBlank(message = "El medicamento es obligatorio")
    private String medicamento;

    @Schema(description = "Dosis indicada", example = "1 comprimido")
    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;

    @Schema(description = "Frecuencia de administración", example = "Cada 12 horas")
    @NotBlank(message = "La frecuencia es obligatoria")
    private String frecuencia;

    @Schema(description = "Duración del tratamiento", example = "7 días")
    @NotBlank(message = "La duración es obligatoria")
    private String duracion;

    @Schema(description = "Indicaciones para el tutor", example = "Administrar con alimento")
    @NotBlank(message = "Las indicaciones son obligatorias")
    private String indicaciones;

    @Schema(description = "Fecha de emisión de la receta", example = "2026-03-15")
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
