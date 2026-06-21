package com.pgvet.receta.controller;

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

import com.pgvet.receta.dto.RecetaCreateDTO;
import com.pgvet.receta.dto.RecetaDTO;
import com.pgvet.receta.service.RecetaService;

@Tag(name = "Recetas", description = "Operaciones de emisión y consulta de recetas médicas")
@RestController
@RequestMapping("/api/v1/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @Operation(summary = "Listar recetas",
               description = "Retorna todas las recetas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<RecetaDTO>> listar() {
        return ResponseEntity.ok(recetaService.listar());
    }

    @Operation(summary = "Buscar receta por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta encontrada"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID único de la receta", required = true)
            @PathVariable Long id) {

        Optional<RecetaDTO> receta = recetaService.buscarPorId(id);

        if (receta.isPresent()) {
            return ResponseEntity.ok(receta.get());
        }

        return ResponseEntity
                .status(404)
                .body("Receta no encontrada");
    }

    @Operation(summary = "Registrar nueva receta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Receta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Referencia externa no encontrada"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody RecetaCreateDTO dto) {

        RecetaDTO nuevaReceta = recetaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaReceta);
    }

    @Operation(summary = "Actualizar receta existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Receta o referencia externa no encontrada"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID único de la receta", required = true)
            @PathVariable Long id,
            @Valid @RequestBody RecetaCreateDTO dto) {

        RecetaDTO recetaActualizada = recetaService.actualizar(id, dto);

        return ResponseEntity.ok(recetaActualizada);
    }

    @Operation(summary = "Eliminar receta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID único de la receta", required = true)
            @PathVariable Long id) {

        recetaService.eliminar(id);

        return ResponseEntity.ok("Receta eliminada correctamente");
    }
}
