package com.pgvet.mascota.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pgvet.mascota.dto.MascotaCreateDTO;
import com.pgvet.mascota.dto.MascotaDTO;
import com.pgvet.mascota.service.MascotaService;

import java.util.Optional;

// Controlador REST de mascotas
@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // GET -> listar mascotas
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listar() {
        return ResponseEntity.ok(mascotaService.listar());
    }

    // GET -> buscar mascota por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        Optional<MascotaDTO> mascota = mascotaService.buscarPorId(id);

        if (mascota.isPresent()) {
            return ResponseEntity.ok(mascota.get());
        }

        return ResponseEntity
                .status(404)
                .body("Mascota no encontrada");
    }

    // POST -> crear mascota
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody MascotaCreateDTO dto) {

        MascotaDTO nuevaMascota = mascotaService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevaMascota);
    }

    // PUT -> actualizar mascota
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MascotaCreateDTO dto) {

        MascotaDTO mascotaActualizada = mascotaService.actualizar(id, dto);

        return ResponseEntity.ok(mascotaActualizada);
    }

    // DELETE -> eliminar mascota
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        mascotaService.eliminar(id);

        return ResponseEntity.ok("Mascota eliminada correctamente");
    }
}