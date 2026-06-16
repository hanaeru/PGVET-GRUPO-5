package com.pgvet.ficha.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Esta clase representa la tabla fichas_clinicas en la base de datos
@Entity
@Table(name = "fichas_clinicas")
public class FichaClinica {

    // ID único de la ficha clínica
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID de la mascota atendida
    private Long mascotaId;

    // ID del veterinario que realizó la atención
    private Long veterinarioId;

    // ID de la cita asociada a esta atención
    private Long citaId;

    // Fecha en que se realizó la atención
    private LocalDate fechaAtencion;

    // Síntomas observados en la mascota
    private String sintomas;

    // Diagnóstico realizado por el veterinario
    private String diagnostico;

    // Tratamiento indicado para la mascota
    private String tratamiento;

    // Comentarios adicionales de la atención
    private String observaciones;

    // Peso de la mascota al momento de la atención
    private Double peso;

    // Temperatura registrada de la mascota
    private Double temperatura;

    // Constructor vacío obligatorio para JPA
    public FichaClinica() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    public String getSintomas() {
        return sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Double getPeso() {
        return peso;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }
}
