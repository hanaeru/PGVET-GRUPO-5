package com.pgvet.cita.controller;

import java.time.LocalDate;
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

import com.pgvet.cita.dto.CitaCreateDTO;
import com.pgvet.cita.dto.CitaDTO;
import com.pgvet.cita.service.CitaService;

@Tag(name = "Citas", description = "Operaciones de agendamiento y consulta de citas")
@RestController
@RequestMapping("/api/v1/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @Operation(summary = "Listar citas",
               description = "Retorna todas las citas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<CitaDTO>> listar() {
        return ResponseEntity.ok(citaService.listar());
    }

    @Operation(summary = "Buscar cita por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cita encontrada"),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID único de la cita", required = true)
            @PathVariable Long id) {

        Optional<CitaDTO> cita = citaService.buscarPorId(id);

        if (cita.isPresent()) {
            return ResponseEntity.ok(cita.get());
        }

        return ResponseEntity
                .status(404)
                .body("Cita no encontrada");
    }

    @Operation(summary = "Buscar citas por mascota")
    @ApiResponse(responseCode = "200", description = "Lista de citas de la mascota")
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<CitaDTO>> buscarPorMascota(
            @Parameter(description = "ID de la mascota", required = true)
            @PathVariable Long mascotaId) {
        return ResponseEntity.ok(citaService.buscarPorMascota(mascotaId));
    }

    @Operation(summary = "Buscar citas por tutor")
    @ApiResponse(responseCode = "200", description = "Lista de citas del tutor")
    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<CitaDTO>> buscarPorTutor(
            @Parameter(description = "ID del tutor (usuario)", required = true)
            @PathVariable Long tutorId) {
        return ResponseEntity.ok(citaService.buscarPorTutor(tutorId));
    }

    @Operation(summary = "Buscar citas por veterinario")
    @ApiResponse(responseCode = "200", description = "Lista de citas del veterinario")
    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<CitaDTO>> buscarPorVeterinario(
            @Parameter(description = "ID del veterinario (usuario)", required = true)
            @PathVariable Long veterinarioId) {
        return ResponseEntity.ok(citaService.buscarPorVeterinario(veterinarioId));
    }

    @Operation(summary = "Buscar citas por fecha")
    @ApiResponse(responseCode = "200", description = "Lista de citas en la fecha indicada")
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaDTO>> buscarPorFecha(
            @Parameter(description = "Fecha en formato ISO yyyy-MM-dd", example = "2026-06-16", required = true)
            @PathVariable String fecha) {
        LocalDate fechaConvertida = LocalDate.parse(fecha);
        return ResponseEntity.ok(citaService.buscarPorFecha(fechaConvertida));
    }

    @Operation(summary = "Buscar citas por estado")
    @ApiResponse(responseCode = "200", description = "Lista de citas con el estado indicado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaDTO>> buscarPorEstado(
            @Parameter(description = "Estado de la cita", example = "PENDIENTE", required = true)
            @PathVariable String estado) {
        return ResponseEntity.ok(citaService.buscarPorEstado(estado));
    }

    @Operation(summary = "Registrar nueva cita")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cita creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Mascota, tutor o veterinario no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody CitaCreateDTO dto) {

        CitaDTO nuevaCita = citaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaCita);
    }

    @Operation(summary = "Actualizar cita existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Cita o referencia externa no encontrada"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID único de la cita", required = true)
            @PathVariable Long id,
            @Valid @RequestBody CitaCreateDTO dto) {

        CitaDTO citaActualizada = citaService.actualizar(id, dto);

        return ResponseEntity.ok(citaActualizada);
    }

    @Operation(summary = "Eliminar cita")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID único de la cita", required = true)
            @PathVariable Long id) {

        citaService.eliminar(id);

        return ResponseEntity.ok("Cita eliminada correctamente");
    }
}
