package com.pgvet.usuario.controller;

import jakarta.validation.Valid;

// Imports de Swagger (agregar estos)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pgvet.usuario.dto.UsuarioCreateDTO;
import com.pgvet.usuario.dto.UsuarioDTO;
import com.pgvet.usuario.service.UsuarioService;

import java.util.List;
import java.util.Optional;

// Controlador REST de usuarios
// ↓ NUEVO: agrupa los endpoints bajo el nombre "Mascotas" en Swagger UI
@Tag(name = "Usuarios", description = "Operaciones de gestión de usuarios")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET -> listar usuarios
    @Operation(summary = "Listar todas los usuarios",
               description = "Retorna la lista completa de usuarios registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listar());
    }

    // GET -> buscar usuario por ID
    //Swagger
      @Operation(summary = "Buscar usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
         @Parameter(description = "ID único del usuario", required = true)
        @PathVariable Long id) {

        Optional<UsuarioDTO> usuario = usuarioService.buscarPorId(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }

        return ResponseEntity
                .status(404)
                .body("Usuario no encontrado");
    }

    // POST -> crear usuario
    //Swagger
    @Operation(summary = "Registrar nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody UsuarioCreateDTO dto) {

        UsuarioDTO nuevoUsuario = usuarioService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevoUsuario);
    }

    // PUT -> actualizar usuario
    //Swagger
     @Operation(summary = "Actualizar usuario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioCreateDTO dto) {

        UsuarioDTO usuarioActualizado = usuarioService.actualizar(id, dto);

        return ResponseEntity.ok(usuarioActualizado);
    }

    // DELETE -> eliminar usuario
    //SWAGGER
     @Operation(summary = "Eliminar usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        usuarioService.eliminar(id);

        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}