package com.pgvet.auth.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pgvet.auth.dto.UsuarioAuthCreateDTO;
import com.pgvet.auth.dto.UsuarioAuthDTO;
import com.pgvet.auth.service.UsuarioAuthService;

import java.util.List;
import java.util.Optional;

// Controlador REST de Auth-Service
@RestController
@RequestMapping("/api/v1/auth")
public class UsuarioAuthController {

    private final UsuarioAuthService usuarioAuthService;

    public UsuarioAuthController(UsuarioAuthService usuarioAuthService) {
        this.usuarioAuthService = usuarioAuthService;
    }

    // GET -> listar usuarios auth
    @GetMapping
    public ResponseEntity<List<UsuarioAuthDTO>> listar() {
        return ResponseEntity.ok(usuarioAuthService.listar());
    }

    // GET -> buscar usuario auth por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        Optional<UsuarioAuthDTO> usuario = usuarioAuthService.buscarPorId(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }

        return ResponseEntity
                .status(404)
                .body("Usuario auth no encontrado");
    }

    // POST -> crear usuario auth
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody UsuarioAuthCreateDTO dto) {

        UsuarioAuthDTO nuevoUsuario = usuarioAuthService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevoUsuario);
    }

    // PUT -> actualizar usuario auth
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioAuthCreateDTO dto) {

        UsuarioAuthDTO usuarioActualizado = usuarioAuthService.actualizar(id, dto);

        return ResponseEntity.ok(usuarioActualizado);
    }

    // DELETE -> eliminar usuario auth
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        usuarioAuthService.eliminar(id);

        return ResponseEntity.ok("Usuario auth eliminado correctamente");
    }
}