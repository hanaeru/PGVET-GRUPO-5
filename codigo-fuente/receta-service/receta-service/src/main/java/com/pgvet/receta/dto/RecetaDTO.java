package com.pgvet.receta.dto;

import java.time.LocalDate;

// DTO de salida
// Este objeto se devuelve al cliente
public class RecetaDTO {

    private Long id;
    private Long mascotaId;
    private Long veterinarioId;
    private Long fichaClinicaId;
    private String medicamento;
    private String dosis;
    private String frecuencia;
    private String duracion;
    private String indicaciones;
    private LocalDate fechaEmision;

    public RecetaDTO() {
    }

    public RecetaDTO(Long id, Long mascotaId, Long veterinarioId, Long fichaClinicaId,
                     String medicamento, String dosis, String frecuencia, String duracion,
                     String indicaciones, LocalDate fechaEmision) {
        this.id = id;
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
        this.fichaClinicaId = fichaClinicaId;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.indicaciones = indicaciones;
        this.fechaEmision = fechaEmision;
    }

    public Long getId() { return id; }
    public Long getMascotaId() { return mascotaId; }
    public Long getVeterinarioId() { return veterinarioId; }
    public Long getFichaClinicaId() { return fichaClinicaId; }
    public String getMedicamento() { return medicamento; }
    public String getDosis() { return dosis; }
    public String getFrecuencia() { return frecuencia; }
    public String getDuracion() { return duracion; }
    public String getIndicaciones() { return indicaciones; }
    public LocalDate getFechaEmision() { return fechaEmision; }
}
