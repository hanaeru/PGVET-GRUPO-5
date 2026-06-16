package com.pgvet.inventario.dto;

import java.time.LocalDate;

// DTO de salida
// Este objeto se devuelve al cliente
public class ProductoInventarioDTO {

    private Long id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private Integer stock;
    private Integer stockMinimo;
    private Integer precio;
    private LocalDate fechaVencimiento;
    private Boolean activo;

    public ProductoInventarioDTO() {
    }

    public ProductoInventarioDTO(Long id, String nombre, String categoria, String descripcion,
                                 Integer stock, Integer stockMinimo, Integer precio,
                                 LocalDate fechaVencimiento, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.precio = precio;
        this.fechaVencimiento = fechaVencimiento;
        this.activo = activo;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public Integer getStock() { return stock; }
    public Integer getStockMinimo() { return stockMinimo; }
    public Integer getPrecio() { return precio; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public Boolean getActivo() { return activo; }
}
