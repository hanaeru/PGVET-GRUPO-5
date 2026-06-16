package com.pgvet.pago.dto;

import java.time.LocalDateTime;

// DTO de salida
// Este objeto se devuelve al cliente
public class PagoDTO {

    private Long id;
    private Long tutorId;
    private Long citaId;
    private Integer monto;
    private String metodoPago;
    private String estado;
    private String numeroBoleta;
    private LocalDateTime fechaPago;

    public PagoDTO() {
    }

    public PagoDTO(Long id, Long tutorId, Long citaId, Integer monto, String metodoPago,
                   String estado, String numeroBoleta, LocalDateTime fechaPago) {
        this.id = id;
        this.tutorId = tutorId;
        this.citaId = citaId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.numeroBoleta = numeroBoleta;
        this.fechaPago = fechaPago;
    }

    public Long getId() { return id; }
    public Long getTutorId() { return tutorId; }
    public Long getCitaId() { return citaId; }
    public Integer getMonto() { return monto; }
    public String getMetodoPago() { return metodoPago; }
    public String getEstado() { return estado; }
    public String getNumeroBoleta() { return numeroBoleta; }
    public LocalDateTime getFechaPago() { return fechaPago; }
}
