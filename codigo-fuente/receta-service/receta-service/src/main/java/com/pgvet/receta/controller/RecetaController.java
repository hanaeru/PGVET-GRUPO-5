package com.pgvet.receta.controller;

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

import com.pgvet.receta.dto.RecetaCreateDTO;
import com.pgvet.receta.dto.RecetaDTO;
import com.pgvet.receta.service.RecetaService;

// Controlador REST de recetas
@RestController
@RequestMapping("/api/v1/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    // GET -> listar recetas
    @GetMapping
    public ResponseEntity<List<RecetaDTO>> listar() {
        return ResponseEntity.ok(recetaService.listar());
    }

    // GET -> buscar receta por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        Optional<RecetaDTO> receta = recetaService.buscarPorId(id);

        if (receta.isPresent()) {
            return ResponseEntity.ok(receta.get());
        }

        return ResponseEntity
                .status(404)
                .body("Receta no encontrada");
    }

    // POST -> crear receta
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody RecetaCreateDTO dto) {

        RecetaDTO nuevaReceta = recetaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaReceta);
    }

    // PUT -> actualizar receta
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RecetaCreateDTO dto) {

        RecetaDTO recetaActualizada = recetaService.actualizar(id, dto);

        return ResponseEntity.ok(recetaActualizada);
    }

    // DELETE -> eliminar receta
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        recetaService.eliminar(id);

        return ResponseEntity.ok("Receta eliminada correctamente");
    }
}
