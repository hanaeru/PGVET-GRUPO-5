package com.pgvet.vacuna.controller;

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

import com.pgvet.vacuna.dto.VacunaCreateDTO;
import com.pgvet.vacuna.dto.VacunaDTO;
import com.pgvet.vacuna.service.VacunaService;

@Tag(name = "Vacunas", description = "Operaciones de registro y consulta de vacunas")
@RestController
@RequestMapping("/api/v1/vacunas")
public class VacunaController {

    private final VacunaService vacunaService;

    public VacunaController(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
    }

    @Operation(summary = "Listar vacunas",
               description = "Retorna todas las vacunas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<VacunaDTO>> listar() {
        return ResponseEntity.ok(vacunaService.listar());
    }

    @Operation(summary = "Buscar vacuna por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vacuna encontrada"),
        @ApiResponse(responseCode = "404", description = "Vacuna no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID único de la vacuna", required = true)
            @PathVariable Long id) {

        Optional<VacunaDTO> vacuna = vacunaService.buscarPorId(id);

        if (vacuna.isPresent()) {
            return ResponseEntity.ok(vacuna.get());
        }

        return ResponseEntity
                .status(404)
                .body("Vacuna no encontrada");
    }

    @Operation(summary = "Registrar nueva vacuna")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Vacuna creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Mascota o veterinario no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody VacunaCreateDTO dto) {

        VacunaDTO nuevaVacuna = vacunaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaVacuna);
    }

    @Operation(summary = "Actualizar vacuna existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Vacuna o referencia externa no encontrada"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID único de la vacuna", required = true)
            @PathVariable Long id,
            @Valid @RequestBody VacunaCreateDTO dto) {

        VacunaDTO vacunaActualizada = vacunaService.actualizar(id, dto);

        return ResponseEntity.ok(vacunaActualizada);
    }

    @Operation(summary = "Eliminar vacuna")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Vacuna no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID único de la vacuna", required = true)
            @PathVariable Long id) {

        vacunaService.eliminar(id);

        return ResponseEntity.ok("Vacuna eliminada correctamente");
    }
}
