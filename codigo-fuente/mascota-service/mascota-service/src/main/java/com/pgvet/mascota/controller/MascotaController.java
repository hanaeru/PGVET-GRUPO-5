package com.pgvet.mascota.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pgvet.mascota.dto.MascotaCreateDTO;
import com.pgvet.mascota.dto.MascotaDTO;
import com.pgvet.mascota.service.MascotaService;

@Tag(name = "Mascotas", description = "Operaciones de gestión de mascotas")
@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @Operation(summary = "Listar mascotas",
               description = "Retorna todas las mascotas registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listar() {
        return ResponseEntity.ok(mascotaService.listar());
    }

    @Operation(summary = "Buscar mascota por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID único de la mascota", required = true)
            @PathVariable Long id) {

        Optional<MascotaDTO> mascota = mascotaService.buscarPorId(id);

        if (mascota.isPresent()) {
            return ResponseEntity.ok(mascota.get());
        }

        return ResponseEntity
                .status(404)
                .body("Mascota no encontrada");
    }

    @Operation(summary = "Registrar nueva mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mascota creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Tutor no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody MascotaCreateDTO dto) {

        MascotaDTO nuevaMascota = mascotaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaMascota);
    }

    @Operation(summary = "Actualizar mascota existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Mascota o tutor no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID único de la mascota", required = true)
            @PathVariable Long id,
            @Valid @RequestBody MascotaCreateDTO dto) {

        MascotaDTO mascotaActualizada = mascotaService.actualizar(id, dto);

        return ResponseEntity.ok(mascotaActualizada);
    }

    @Operation(summary = "Eliminar mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID único de la mascota", required = true)
            @PathVariable Long id) {

        mascotaService.eliminar(id);

        return ResponseEntity.ok("Mascota eliminada correctamente");
    }
}
