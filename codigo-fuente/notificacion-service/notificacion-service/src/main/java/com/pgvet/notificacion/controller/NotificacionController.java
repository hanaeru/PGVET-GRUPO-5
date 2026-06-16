package com.pgvet.notificacion.controller;

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

import com.pgvet.notificacion.dto.NotificacionCreateDTO;
import com.pgvet.notificacion.dto.NotificacionDTO;
import com.pgvet.notificacion.service.NotificacionService;

// Controlador REST de notificaciones
@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    // GET -> listar notificaciones
    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listar() {
        return ResponseEntity.ok(notificacionService.listar());
    }

    // GET -> buscar notificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        Optional<NotificacionDTO> notificacion = notificacionService.buscarPorId(id);

        if (notificacion.isPresent()) {
            return ResponseEntity.ok(notificacion.get());
        }

        return ResponseEntity
                .status(404)
                .body("Notificación no encontrada");
    }

    // POST -> crear notificación
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody NotificacionCreateDTO dto) {

        NotificacionDTO nuevaNotificacion = notificacionService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaNotificacion);
    }

    // PUT -> actualizar notificación
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody NotificacionCreateDTO dto) {

        NotificacionDTO notificacionActualizada = notificacionService.actualizar(id, dto);

        return ResponseEntity.ok(notificacionActualizada);
    }

    // DELETE -> eliminar notificación
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        notificacionService.eliminar(id);

        return ResponseEntity.ok("Notificación eliminada correctamente");
    }
}
