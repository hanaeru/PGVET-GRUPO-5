package com.pgvet.notificacion.controller;

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

import com.pgvet.notificacion.dto.NotificacionCreateDTO;
import com.pgvet.notificacion.dto.NotificacionDTO;
import com.pgvet.notificacion.service.NotificacionService;

@Tag(name = "Notificaciones", description = "Operaciones de envío y consulta de notificaciones")
@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Operation(summary = "Listar notificaciones",
               description = "Retorna todas las notificaciones registradas.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listar() {
        return ResponseEntity.ok(notificacionService.listar());
    }

    @Operation(summary = "Buscar notificación por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID único de la notificación", required = true)
            @PathVariable Long id) {

        Optional<NotificacionDTO> notificacion = notificacionService.buscarPorId(id);

        if (notificacion.isPresent()) {
            return ResponseEntity.ok(notificacion.get());
        }

        return ResponseEntity
                .status(404)
                .body("Notificación no encontrada");
    }

    @Operation(summary = "Registrar nueva notificación")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody NotificacionCreateDTO dto) {

        NotificacionDTO nuevaNotificacion = notificacionService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaNotificacion);
    }

    @Operation(summary = "Actualizar notificación existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Notificación o usuario no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID único de la notificación", required = true)
            @PathVariable Long id,
            @Valid @RequestBody NotificacionCreateDTO dto) {

        NotificacionDTO notificacionActualizada = notificacionService.actualizar(id, dto);

        return ResponseEntity.ok(notificacionActualizada);
    }

    @Operation(summary = "Eliminar notificación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID único de la notificación", required = true)
            @PathVariable Long id) {

        notificacionService.eliminar(id);

        return ResponseEntity.ok("Notificación eliminada correctamente");
    }
}
