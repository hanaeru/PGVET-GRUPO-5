package com.pgvet.mascota.dto;

// DTO de salida
// Este objeto se devuelve al cliente
public class MascotaDTO {

    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private Integer edad;
    private Double peso;
    private String color;
    private String microchip;
    private Long tutorId;
    private Boolean activo;

    public MascotaDTO() {
    }

    public MascotaDTO(Long id, String nombre, String especie, String raza, String sexo,
                      Integer edad, Double peso, String color, String microchip,
                      Long tutorId, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.edad = edad;
        this.peso = peso;
        this.color = color;
        this.microchip = microchip;
        this.tutorId = tutorId;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getRaza() { return raza; }
    public String getSexo() { return sexo; }
    public Integer getEdad() { return edad; }
    public Double getPeso() { return peso; }
    public String getColor() { return color; }
    public String getMicrochip() { return microchip; }
    public Long getTutorId() { return tutorId; }
    public Boolean getActivo() { return activo; }
}
