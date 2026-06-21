package com.pgvet.inventario.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

@Tag(name = "Inventario", description = "Operaciones de gestión de productos e inventario")
@RestController
@RequestMapping("/api/v1/inventario")
public class ProductoInventarioController {

    private final ProductoInventarioService productoInventarioService;

    public ProductoInventarioController(ProductoInventarioService productoInventarioService) {
        this.productoInventarioService = productoInventarioService;
    }

    @Operation(summary = "Listar productos",
               description = "Retorna todos los productos del inventario.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ProductoInventarioDTO>> listar() {
        return ResponseEntity.ok(productoInventarioService.listar());
    }

    @Operation(summary = "Buscar producto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID único del producto", required = true)
            @PathVariable Long id) {

        Optional<ProductoInventarioDTO> producto = productoInventarioService.buscarPorId(id);

        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        }

        return ResponseEntity
                .status(404)
                .body("Producto no encontrado");
    }

    @Operation(summary = "Registrar nuevo producto")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ProductoInventarioCreateDTO dto) {

        ProductoInventarioDTO nuevoProducto = productoInventarioService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevoProducto);
    }

    @Operation(summary = "Actualizar producto existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID único del producto", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ProductoInventarioCreateDTO dto) {

        ProductoInventarioDTO productoActualizado = productoInventarioService.actualizar(id, dto);

        return ResponseEntity.ok(productoActualizado);
    }

    @Operation(summary = "Eliminar producto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID único del producto", required = true)
            @PathVariable Long id) {

        productoInventarioService.eliminar(id);

        return ResponseEntity.ok("Producto eliminado correctamente");
    }
}
