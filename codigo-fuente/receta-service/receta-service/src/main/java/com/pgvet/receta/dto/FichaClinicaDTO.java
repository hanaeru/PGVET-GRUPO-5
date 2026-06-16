package com.pgvet.receta.dto;

// DTO espejo de Ficha-Clinica-Service para Feign
public class FichaClinicaDTO {

    private Long id;

    public FichaClinicaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
