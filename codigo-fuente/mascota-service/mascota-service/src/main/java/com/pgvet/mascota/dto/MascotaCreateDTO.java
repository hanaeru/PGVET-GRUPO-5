package com.pgvet.mascota.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Datos para crear o actualizar una mascota")
public class MascotaCreateDTO {

    @Schema(description = "Nombre de la mascota", example = "Luna")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Especie del animal", example = "Perro")
    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    @Schema(description = "Raza del animal", example = "Labrador")
    @NotBlank(message = "La raza es obligatoria")
    private String raza;

    @Schema(description = "Sexo del animal", example = "Hembra")
    @NotBlank(message = "El sexo es obligatorio")
    private String sexo;

    @Schema(description = "Edad en años", example = "3")
    @NotNull(message = "La edad es obligatoria")
    @PositiveOrZero(message = "La edad no puede ser negativa")
    private Integer edad;

    @Schema(description = "Peso en kilogramos", example = "12.5")
    @NotNull(message = "El peso es obligatorio")
    @PositiveOrZero(message = "El peso no puede ser negativo")
    private Double peso;

    @Schema(description = "Color predominante", example = "Negro")
    @NotBlank(message = "El color es obligatorio")
    private String color;

    @Schema(description = "Número de microchip (opcional)", example = "985112004567890")
    private String microchip;

    @Schema(description = "ID del tutor (usuario) propietario", example = "1")
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
