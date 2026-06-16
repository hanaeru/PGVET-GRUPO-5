package com.pgvet.pago.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

// DTO de entrada
// Este objeto recibe el JSON para crear o actualizar pagos
public class PagoCreateDTO {

    @NotNull(message = "El tutor es obligatorio")
    private Long tutorId;

    @NotNull(message = "La cita es obligatoria")
    private Long citaId;

    @NotNull(message = "El monto es obligatorio")
    @PositiveOrZero(message = "El monto no puede ser negativo")
    private Integer monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotBlank(message = "El número de boleta es obligatorio")
    private String numeroBoleta;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDateTime fechaPago;

    public PagoCreateDTO() {
    }

    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }

    public Long getCitaId() { return citaId; }
    public void setCitaId(Long citaId) { this.citaId = citaId; }

    public Integer getMonto() { return monto; }
    public void setMonto(Integer monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNumeroBoleta() { return numeroBoleta; }
    public void setNumeroBoleta(String numeroBoleta) { this.numeroBoleta = numeroBoleta; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
}
