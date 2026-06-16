package com.pgvet.vacuna.dto;

import java.time.LocalDate;

// DTO de salida
// Este objeto se devuelve en las respuestas del servicio
public class VacunaDTO {

    private Long id;
    private Long mascotaId;
    private Long veterinarioId;
    private String nombreVacuna;
    private LocalDate fechaAplicacion;
    private LocalDate proximaDosis;
    private String lote;
    private String observacion;

    public VacunaDTO() {
    }

    public VacunaDTO(Long id, Long mascotaId, Long veterinarioId, String nombreVacuna,
                     LocalDate fechaAplicacion, LocalDate proximaDosis,
                     String lote, String observacion) {
        this.id = id;
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
        this.nombreVacuna = nombreVacuna;
        this.fechaAplicacion = fechaAplicacion;
        this.proximaDosis = proximaDosis;
        this.lote = lote;
        this.observacion = observacion;
    }

    public Long getId() {
        return id;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public LocalDate getProximaDosis() {
        return proximaDosis;
    }

    public String getLote() {
        return lote;
    }

    public String getObservacion() {
        return observacion;
    }
}
