package com.pgvet.ficha.dto;

// DTO espejo de Mascota-Service para Feign
public class MascotaDTO {

    private Long id;

    public MascotaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
