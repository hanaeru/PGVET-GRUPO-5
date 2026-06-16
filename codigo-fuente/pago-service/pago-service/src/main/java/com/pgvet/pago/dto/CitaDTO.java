package com.pgvet.pago.dto;

// DTO espejo de Cita-Service para Feign
public class CitaDTO {

    private Long id;

    public CitaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
