package com.pgvet.vacuna.controller;


import java.util.List;
import java.util.Optional;

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

import com.pgvet.vacuna.dto.VacunaCreateDTO;
import com.pgvet.vacuna.dto.VacunaDTO;
import com.pgvet.vacuna.service.VacunaService;

import jakarta.validation.Valid;

// Controlador REST de vacunas
@RestController
@RequestMapping("/api/v1/vacunas")
public class VacunaController {

    private final VacunaService vacunaService;

    public VacunaController(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
    }

    // GET -> listar vacunas
    @GetMapping
    public ResponseEntity<List<VacunaDTO>> listar() {
        return ResponseEntity.ok(vacunaService.listar());
    }

    // GET -> buscar vacuna por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        Optional<VacunaDTO> vacuna = vacunaService.buscarPorId(id);

        if (vacuna.isPresent()) {
            return ResponseEntity.ok(vacuna.get());
        }

        return ResponseEntity
                .status(404)
                .body("Vacuna no encontrada");
    }

    // POST -> crear vacuna
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody VacunaCreateDTO dto) {

        VacunaDTO nuevaVacuna = vacunaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaVacuna);
    }

    // PUT -> actualizar vacuna
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VacunaCreateDTO dto) {

        VacunaDTO vacunaActualizada = vacunaService.actualizar(id, dto);

        return ResponseEntity.ok(vacunaActualizada);
    }

    // DELETE -> eliminar vacuna
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        vacunaService.eliminar(id);

        return ResponseEntity.ok("Vacuna eliminada correctamente");
    }
}