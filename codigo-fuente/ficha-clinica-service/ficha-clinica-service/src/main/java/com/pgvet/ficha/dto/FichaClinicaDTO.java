package com.pgvet.ficha.dto;

import java.time.LocalDate;

// DTO de salida
// Este objeto se devuelve al cliente
public class FichaClinicaDTO {

    private Long id;
    private Long mascotaId;
    private Long veterinarioId;
    private Long citaId;
    private LocalDate fechaAtencion;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private Double peso;
    private Double temperatura;

    public FichaClinicaDTO() {
    }

    public FichaClinicaDTO(Long id, Long mascotaId, Long veterinarioId, Long citaId,
                           LocalDate fechaAtencion, String sintomas, String diagnostico,
                           String tratamiento, String observaciones, Double peso,
                           Double temperatura) {
        this.id = id;
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
        this.citaId = citaId;
        this.fechaAtencion = fechaAtencion;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
        this.peso = peso;
        this.temperatura = temperatura;
    }

    public Long getId() { return id; }
    public Long getMascotaId() { return mascotaId; }
    public Long getVeterinarioId() { return veterinarioId; }
    public Long getCitaId() { return citaId; }
    public LocalDate getFechaAtencion() { return fechaAtencion; }
    public String getSintomas() { return sintomas; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public String getObservaciones() { return observaciones; }
    public Double getPeso() { return peso; }
    public Double getTemperatura() { return temperatura; }
}
