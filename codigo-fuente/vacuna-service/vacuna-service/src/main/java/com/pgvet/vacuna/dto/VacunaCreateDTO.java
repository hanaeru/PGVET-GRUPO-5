package com.pgvet.vacuna.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// DTO de entrada
// Este objeto recibe el JSON cuando se crea o actualiza una vacuna
public class VacunaCreateDTO {

    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;

    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId;

    @NotBlank(message = "El nombre de la vacuna es obligatorio")
    private String nombreVacuna;

    @NotNull(message = "La fecha de aplicación es obligatoria")
    private LocalDate fechaAplicacion;

    @NotNull(message = "La próxima dosis es obligatoria")
    private LocalDate proximaDosis;

    @NotBlank(message = "El lote es obligatorio")
    private String lote;

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
