package com.pgvet.inventario.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgvet.inventario.dto.ProductoInventarioCreateDTO;
import com.pgvet.inventario.dto.ProductoInventarioDTO;
import com.pgvet.inventario.service.ProductoInventarioService;

// Controlador REST de inventario
@RestController
@RequestMapping("/api/v1/inventario")
public class ProductoInventarioController {

    private final ProductoInventarioService productoService;

    public ProductoInventarioController(ProductoInventarioService productoService) {
        this.productoService = productoService;
    }

    // GET -> listar productos
    @GetMapping
    public ResponseEntity<List<ProductoInventarioDTO>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    // GET -> buscar producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        Optional<ProductoInventarioDTO> producto = productoService.buscarPorId(id);

        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        }

        return ResponseEntity
                .status(404)
                .body("Producto no encontrado");
    }

    // POST -> crear producto
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ProductoInventarioCreateDTO dto) {

        ProductoInventarioDTO nuevoProducto = productoService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevoProducto);
    }

    // PUT -> actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoInventarioCreateDTO dto) {

        ProductoInventarioDTO productoActualizado = productoService.actualizar(id, dto);

        return ResponseEntity.ok(productoActualizado);
    }

    // DELETE -> eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        productoService.eliminar(id);

        return ResponseEntity.ok("Producto eliminado correctamente");
    }
}
