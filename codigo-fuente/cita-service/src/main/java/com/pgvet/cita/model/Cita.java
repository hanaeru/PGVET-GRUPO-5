package com.pgvet.cita.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Esta clase representa la tabla citas en la base de datos
@Entity
@Table(name = "citas")
public class Cita {

    // ID único de la cita
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID de la mascota asociada a la cita
    private Long mascotaId;

    // ID del tutor o dueño de la mascota
    private Long tutorId;

    // ID del veterinario asignado
    private Long veterinarioId;

    // Fecha de la cita
    private LocalDate fecha;

    // Hora de la cita
    private LocalTime hora;

    // Motivo de la consulta veterinaria
    private String motivo;

    // Estado de la cita: AGENDADA, CONFIRMADA, CANCELADA o REALIZADA
    private String estado;

    // Observación adicional de la cita
    private String observacion;

    // Constructor vacío obligatorio para JPA
    public Cita() {
        this.estado = "AGENDADA";
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getEstado() {
        return estado;
    }

    public String getObservacion() {
        return observacion;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}
