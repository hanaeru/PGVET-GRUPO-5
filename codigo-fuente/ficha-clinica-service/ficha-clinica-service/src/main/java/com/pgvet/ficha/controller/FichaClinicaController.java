package com.pgvet.ficha.controller;

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

import com.pgvet.ficha.dto.FichaClinicaCreateDTO;
import com.pgvet.ficha.dto.FichaClinicaDTO;
import com.pgvet.ficha.service.FichaClinicaService;

// Controlador REST de fichas clínicas
@RestController
@RequestMapping("/api/v1/fichas-clinicas")
public class FichaClinicaController {

    private final FichaClinicaService fichaClinicaService;

    public FichaClinicaController(FichaClinicaService fichaClinicaService) {
        this.fichaClinicaService = fichaClinicaService;
    }

    // GET -> listar fichas clínicas
    @GetMapping
    public ResponseEntity<List<FichaClinicaDTO>> listar() {
        return ResponseEntity.ok(fichaClinicaService.listar());
    }

    // GET -> buscar ficha clínica por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        Optional<FichaClinicaDTO> ficha = fichaClinicaService.buscarPorId(id);

        if (ficha.isPresent()) {
            return ResponseEntity.ok(ficha.get());
        }

        return ResponseEntity
                .status(404)
                .body("Ficha clínica no encontrada");
    }

    // GET -> buscar fichas clínicas por mascota
    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<FichaClinicaDTO>> buscarPorMascota(@PathVariable Long mascotaId) {
        return ResponseEntity.ok(fichaClinicaService.buscarPorMascota(mascotaId));
    }

    // GET -> buscar fichas clínicas por veterinario
    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<FichaClinicaDTO>> buscarPorVeterinario(@PathVariable Long veterinarioId) {
        return ResponseEntity.ok(fichaClinicaService.buscarPorVeterinario(veterinarioId));
    }

    // GET -> buscar fichas clínicas por cita
    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<FichaClinicaDTO>> buscarPorCita(@PathVariable Long citaId) {
        return ResponseEntity.ok(fichaClinicaService.buscarPorCita(citaId));
    }

    // POST -> crear ficha clínica
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody FichaClinicaCreateDTO dto) {

        FichaClinicaDTO nuevaFicha = fichaClinicaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaFicha);
    }

    // PUT -> actualizar ficha clínica
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody FichaClinicaCreateDTO dto) {

        FichaClinicaDTO fichaActualizada = fichaClinicaService.actualizar(id, dto);

        return ResponseEntity.ok(fichaActualizada);
    }

    // DELETE -> eliminar ficha clínica
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        fichaClinicaService.eliminar(id);

        return ResponseEntity.ok("Ficha clínica eliminada correctamente");
    }
}
