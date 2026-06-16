package com.pgvet.inventario.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.pgvet.inventario.dto.ProductoInventarioCreateDTO;
import com.pgvet.inventario.dto.ProductoInventarioDTO;
import com.pgvet.inventario.model.ProductoInventario;
import com.pgvet.inventario.repository.ProductoInventarioRepository;

// Marca esta clase como servicio
@Service
public class ProductoInventarioService {

    private static final Logger log = LoggerFactory.getLogger(ProductoInventarioService.class);

    private final ProductoInventarioRepository productoRepository;

    public ProductoInventarioService(ProductoInventarioRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Convierte entidad a DTO de salida
    private ProductoInventarioDTO convertirADTO(ProductoInventario producto) {
        return new ProductoInventarioDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getDescripcion(),
                producto.getStock(),
                producto.getStockMinimo(),
                producto.getPrecio(),
                producto.getFechaVencimiento(),
                producto.getActivo()
        );
    }

    // Convierte DTO de entrada a entidad
    private ProductoInventario convertirAEntidad(ProductoInventarioCreateDTO dto) {
        ProductoInventario producto = new ProductoInventario();

        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setPrecio(dto.getPrecio());
        producto.setFechaVencimiento(dto.getFechaVencimiento());
        producto.setActivo(true);

        return producto;
    }

    // Listar todos los productos
    public List<ProductoInventarioDTO> listar() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar producto por ID
    public Optional<ProductoInventarioDTO> buscarPorId(Long id) {
        return productoRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Guardar producto nuevo
    public ProductoInventarioDTO guardar(ProductoInventarioCreateDTO dto) {
        log.info("Guardando registro");
        ProductoInventario producto = convertirAEntidad(dto);
        ProductoInventario productoGuardado = productoRepository.save(producto);
        return convertirADTO(productoGuardado);
    }

    // Actualizar producto existente
    public ProductoInventarioDTO actualizar(Long id, ProductoInventarioCreateDTO dto) {
        log.info("Actualizando registro");

        ProductoInventario producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setCategoria(dto.getCategoria());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setPrecio(dto.getPrecio());
        producto.setFechaVencimiento(dto.getFechaVencimiento());

        return convertirADTO(productoRepository.save(producto));
    }

    // Eliminar producto
    public void eliminar(Long id) {
        log.info("Eliminando registro id={}", id);

        ProductoInventario producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoRepository.delete(producto);
    }
}
