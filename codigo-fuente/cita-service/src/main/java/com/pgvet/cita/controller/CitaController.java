package com.pgvet.cita.controller;

import java.time.LocalDate;
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

import com.pgvet.cita.dto.CitaCreateDTO;
import com.pgvet.cita.dto.CitaDTO;
import com.pgvet.cita.service.CitaService;

// Controlador REST de citas
@RestController
@RequestMapping("/api/v1/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    // GET -> listar citas
    @GetMapping
    public ResponseEntity<List<CitaDTO>> listar() {
        return ResponseEntity.ok(citaService.listar());
    }

    // GET -> buscar cita por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        Optional<CitaDTO> cita = citaService.buscarPorId(id);

        if (cita.isPresent()) {
            return ResponseEntity.ok(cita.get());
        }

        return ResponseEntity
                .status(404)
                .body("Cita no encontrada");
    }

    // GET -> buscar citas por mascota
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<CitaDTO>> buscarPorMascota(@PathVariable Long mascotaId) {
        return ResponseEntity.ok(citaService.buscarPorMascota(mascotaId));
    }

    // GET -> buscar citas por tutor
    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<CitaDTO>> buscarPorTutor(@PathVariable Long tutorId) {
        return ResponseEntity.ok(citaService.buscarPorTutor(tutorId));
    }

    // GET -> buscar citas por veterinario
    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<CitaDTO>> buscarPorVeterinario(@PathVariable Long veterinarioId) {
        return ResponseEntity.ok(citaService.buscarPorVeterinario(veterinarioId));
    }

    // GET -> buscar citas por fecha
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaDTO>> buscarPorFecha(@PathVariable String fecha) {
        LocalDate fechaConvertida = LocalDate.parse(fecha);
        return ResponseEntity.ok(citaService.buscarPorFecha(fechaConvertida));
    }

    // GET -> buscar citas por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CitaDTO>> buscarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(citaService.buscarPorEstado(estado));
    }

    // POST -> crear cita
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody CitaCreateDTO dto) {

        CitaDTO nuevaCita = citaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaCita);
    }

    // PUT -> actualizar cita
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CitaCreateDTO dto) {

        CitaDTO citaActualizada = citaService.actualizar(id, dto);

        return ResponseEntity.ok(citaActualizada);
    }

    // DELETE -> eliminar cita
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        citaService.eliminar(id);

        return ResponseEntity.ok("Cita eliminada correctamente");
    }
}
