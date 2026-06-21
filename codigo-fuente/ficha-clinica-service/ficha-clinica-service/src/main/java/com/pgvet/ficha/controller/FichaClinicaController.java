package com.pgvet.ficha.controller;

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

import com.pgvet.ficha.dto.FichaClinicaCreateDTO;
import com.pgvet.ficha.dto.FichaClinicaDTO;
import com.pgvet.ficha.service.FichaClinicaService;

@Tag(name = "Fichas clínicas", description = "Operaciones de historial clínico veterinario")
@RestController
@RequestMapping("/api/v1/fichas-clinicas")
public class FichaClinicaController {

    private final FichaClinicaService fichaClinicaService;

    public FichaClinicaController(FichaClinicaService fichaClinicaService) {
        this.fichaClinicaService = fichaClinicaService;
    }

    @Operation(summary = "Listar fichas clínicas",
               description = "Retorna todas las fichas clínicas registradas.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<FichaClinicaDTO>> listar() {
        return ResponseEntity.ok(fichaClinicaService.listar());
    }

    @Operation(summary = "Buscar ficha clínica por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ficha clínica encontrada"),
        @ApiResponse(responseCode = "404", description = "Ficha clínica no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID único de la ficha clínica", required = true)
            @PathVariable Long id) {

        Optional<FichaClinicaDTO> ficha = fichaClinicaService.buscarPorId(id);

        if (ficha.isPresent()) {
            return ResponseEntity.ok(ficha.get());
        }

        return ResponseEntity
                .status(404)
                .body("Ficha clínica no encontrada");
    }

    @Operation(summary = "Buscar fichas clínicas por mascota")
    @ApiResponse(responseCode = "200", description = "Lista de fichas de la mascota")
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<FichaClinicaDTO>> buscarPorMascota(
            @Parameter(description = "ID de la mascota", required = true)
            @PathVariable Long mascotaId) {
        return ResponseEntity.ok(fichaClinicaService.buscarPorMascota(mascotaId));
    }

    @Operation(summary = "Buscar fichas clínicas por veterinario")
    @ApiResponse(responseCode = "200", description = "Lista de fichas del veterinario")
    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<FichaClinicaDTO>> buscarPorVeterinario(
            @Parameter(description = "ID del veterinario (usuario)", required = true)
            @PathVariable Long veterinarioId) {
        return ResponseEntity.ok(fichaClinicaService.buscarPorVeterinario(veterinarioId));
    }

    @Operation(summary = "Buscar fichas clínicas por cita")
    @ApiResponse(responseCode = "200", description = "Lista de fichas asociadas a la cita")
    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<FichaClinicaDTO>> buscarPorCita(
            @Parameter(description = "ID de la cita", required = true)
            @PathVariable Long citaId) {
        return ResponseEntity.ok(fichaClinicaService.buscarPorCita(citaId));
    }

    @Operation(summary = "Registrar nueva ficha clínica")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ficha clínica creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Mascota, veterinario o cita no encontrada"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody FichaClinicaCreateDTO dto) {

        FichaClinicaDTO nuevaFicha = fichaClinicaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaFicha);
    }

    @Operation(summary = "Actualizar ficha clínica existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Ficha o referencia externa no encontrada"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID único de la ficha clínica", required = true)
            @PathVariable Long id,
            @Valid @RequestBody FichaClinicaCreateDTO dto) {

        FichaClinicaDTO fichaActualizada = fichaClinicaService.actualizar(id, dto);

        return ResponseEntity.ok(fichaActualizada);
    }

    @Operation(summary = "Eliminar ficha clínica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Ficha clínica no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID único de la ficha clínica", required = true)
            @PathVariable Long id) {

        fichaClinicaService.eliminar(id);

        return ResponseEntity.ok("Ficha clínica eliminada correctamente");
    }
}
