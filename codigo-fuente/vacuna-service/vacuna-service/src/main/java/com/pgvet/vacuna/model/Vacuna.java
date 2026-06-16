package com.pgvet.vacuna.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Marca esta clase como una entidad de la base de datos
@Entity

// Nombre de la tabla en MySQL
@Table(name = "vacunas")
public class Vacuna {

    // ID autoincremental
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID de la mascota asociada
    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;

    // ID del veterinario
    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId;

    // Nombre de la vacuna
    @NotBlank(message = "El nombre de la vacuna es obligatorio")
    private String nombreVacuna;

    // Fecha en que se aplicó la vacuna
    @NotNull(message = "La fecha de aplicación es obligatoria")
    private LocalDate fechaAplicacion;

    // Próxima fecha de vacunación
    @NotNull(message = "La próxima dosis es obligatoria")
    private LocalDate proximaDosis;

    // Número de lote de la vacuna
    @NotBlank(message = "El lote es obligatorio")
    private String lote;

    // Observaciones adicionales
    private String observacion;

    // Constructor vacío obligatorio para JPA
    public Vacuna() {
    }

    // GETTERS Y SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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