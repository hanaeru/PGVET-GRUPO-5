package com.pgvet.mascota.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

// DTO de entrada
// Este objeto recibe el JSON para crear o actualizar mascota
public class MascotaCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @NotBlank(message = "El sexo es obligatorio")
    private String sexo;

    @NotNull(message = "La edad es obligatoria")
    @PositiveOrZero(message = "La edad no puede ser negativa")
    private Integer edad;

    @NotNull(message = "El peso es obligatorio")
    @PositiveOrZero(message = "El peso no puede ser negativo")
    private Double peso;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    private String microchip;

    @NotNull(message = "El tutor es obligatorio")
    private Long tutorId;

    public MascotaCreateDTO() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getMicrochip() { return microchip; }
    public void setMicrochip(String microchip) { this.microchip = microchip; }

    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }
}