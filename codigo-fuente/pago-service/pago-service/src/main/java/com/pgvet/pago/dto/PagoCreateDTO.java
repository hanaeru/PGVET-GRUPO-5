package com.pgvet.pago.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

// DTO de entrada
// Este objeto recibe el JSON para crear o actualizar pagos
@Schema(description = "Datos para crear o actualizar un pago")
public class PagoCreateDTO {

    @Schema(description = "ID del tutor que realiza el pago", example = "1")
    @NotNull(message = "El tutor es obligatorio")
    private Long tutorId;

    @Schema(description = "ID de la cita asociada al pago", example = "10")
    @NotNull(message = "La cita es obligatoria")
    private Long citaId;

    @Schema(description = "Monto pagado en pesos chilenos", example = "25000")
    @NotNull(message = "El monto es obligatorio")
    @PositiveOrZero(message = "El monto no puede ser negativo")
    private Integer monto;

    @Schema(description = "Método de pago utilizado", example = "Tarjeta débito")
    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @Schema(description = "Estado del pago", example = "COMPLETADO")
    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @Schema(description = "Número de boleta o comprobante", example = "BOL-2026-0042")
    @NotBlank(message = "El número de boleta es obligatorio")
    private String numeroBoleta;

    @Schema(description = "Fecha y hora en que se registró el pago", example = "2026-03-15T14:30:00")
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
