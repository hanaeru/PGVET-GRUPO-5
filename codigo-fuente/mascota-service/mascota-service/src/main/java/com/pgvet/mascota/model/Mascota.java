package com.pgvet.mascota.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Esta clase representa la tabla mascotas en la base de datos
@Entity
@Table(name = "mascotas")
public class Mascota {

    // ID único de la mascota
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la mascota
    private String nombre;

    // Especie: PERRO, GATO u OTRA
    private String especie;

    // Raza de la mascota
    private String raza;

    // Sexo: MACHO o HEMBRA
    private String sexo;

    // Edad aproximada de la mascota
    private Integer edad;

    // Peso de la mascota en kilos
    private Double peso;

    // Color principal de la mascota
    private String color;

    // Número de microchip de la mascota
    private String microchip;

    // ID del tutor dueño de la mascota
    private Long tutorId;

    // Indica si el registro está activo
    private Boolean activo;

    // Constructor vacío obligatorio para JPA
    public Mascota() {
        this.activo = true;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaza() {
        return raza;
    }

    public String getSexo() {
        return sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public Double getPeso() {
        return peso;
    }

    public String getColor() {
        return color;
    }

    public String getMicrochip() {
        return microchip;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public Boolean getActivo() {
        return activo;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
