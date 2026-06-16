package com.pgvet.pago.dto;

// DTO espejo de Usuario-Service para Feign
public class UsuarioDTO {

    private Long id;

    public UsuarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
