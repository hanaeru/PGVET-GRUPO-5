package com.pgvet.vacuna.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// DTO de entrada
// Este objeto recibe el JSON cuando se crea o actualiza una vacuna
@Schema(description = "Datos para crear o actualizar una vacuna")
public class VacunaCreateDTO {

    @Schema(description = "ID de la mascota vacunada", example = "1")
    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;

    @Schema(description = "ID del veterinario que aplicó la vacuna", example = "2")
    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId;

    @Schema(description = "Nombre comercial o tipo de vacuna", example = "Antirrábica")
    @NotBlank(message = "El nombre de la vacuna es obligatorio")
    private String nombreVacuna;

    @Schema(description = "Fecha en que se aplicó la vacuna", example = "2026-03-15")
    @NotNull(message = "La fecha de aplicación es obligatoria")
    private LocalDate fechaAplicacion;

    @Schema(description = "Fecha programada para la próxima dosis", example = "2027-03-15")
    @NotNull(message = "La próxima dosis es obligatoria")
    private LocalDate proximaDosis;

    @Schema(description = "Número de lote del producto", example = "LOT-2026-001")
    @NotBlank(message = "El lote es obligatorio")
    private String lote;

    @Schema(description = "Observaciones adicionales (opcional)", example = "Sin reacciones adversas")
    private String observacion;

    public VacunaCreateDTO() {
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public void setNombreVacuna(String nombreVacuna) {
        this.nombreVacuna = nombreVacuna;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public LocalDate getProximaDosis() {
        return proximaDosis;
    }

    public void setProximaDosis(LocalDate proximaDosis) {
        this.proximaDosis = proximaDosis;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
