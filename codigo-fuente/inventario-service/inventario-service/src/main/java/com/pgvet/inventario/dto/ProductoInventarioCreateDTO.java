package com.pgvet.inventario.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

// DTO de entrada
// Este objeto recibe el JSON para crear o actualizar productos de inventario
@Schema(description = "Datos para crear o actualizar un producto de inventario")
public class ProductoInventarioCreateDTO {

    @Schema(description = "Nombre del producto", example = "Suero fisiológico 500ml")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Categoría del producto", example = "Insumos")
    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @Schema(description = "Descripción del producto (opcional)", example = "Solución estéril para uso veterinario")
    private String descripcion;

    @Schema(description = "Cantidad disponible en stock", example = "50")
    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(description = "Stock mínimo antes de alerta de reposición", example = "10")
    @NotNull(message = "El stock mínimo es obligatorio")
    @PositiveOrZero(message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;

    @Schema(description = "Precio unitario en pesos chilenos", example = "3500")
    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private Integer precio;

    @Schema(description = "Fecha de vencimiento (opcional)", example = "2027-06-30")
    private LocalDate fechaVencimiento;

    public ProductoInventarioCreateDTO() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}
