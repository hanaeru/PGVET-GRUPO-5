package com.pgvet.auth.controller;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pgvet.auth.dto.UsuarioAuthCreateDTO;
import com.pgvet.auth.dto.UsuarioAuthDTO;
import com.pgvet.auth.service.UsuarioAuthService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Auth", description = "Operaciones de autenticación y credenciales")
@RestController
@RequestMapping("/api/v1/auth")
public class UsuarioAuthController {

    private final UsuarioAuthService usuarioAuthService;

    public UsuarioAuthController(UsuarioAuthService usuarioAuthService) {
        this.usuarioAuthService = usuarioAuthService;
    }

    @Operation(summary = "Listar credenciales de auth",
               description = "Retorna todas las credenciales registradas en auth-service.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<UsuarioAuthDTO>> listar() {
        return ResponseEntity.ok(usuarioAuthService.listar());
    }

    @Operation(summary = "Buscar credencial por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Credencial encontrada"),
        @ApiResponse(responseCode = "404", description = "Credencial no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID único de la credencial", required = true)
            @PathVariable Long id) {

        Optional<UsuarioAuthDTO> usuario = usuarioAuthService.buscarPorId(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }

        return ResponseEntity
                .status(404)
                .body("Usuario auth no encontrado");
    }

    @Operation(summary = "Registrar credencial de auth")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Credencial creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody UsuarioAuthCreateDTO dto) {

        UsuarioAuthDTO nuevoUsuario = usuarioAuthService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevoUsuario);
    }

    @Operation(summary = "Actualizar credencial de auth")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Credencial no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID único de la credencial", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UsuarioAuthCreateDTO dto) {

        UsuarioAuthDTO usuarioActualizado = usuarioAuthService.actualizar(id, dto);

        return ResponseEntity.ok(usuarioActualizado);
    }

    @Operation(summary = "Eliminar credencial de auth")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Credencial no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID único de la credencial", required = true)
            @PathVariable Long id) {

        usuarioAuthService.eliminar(id);

        return ResponseEntity.ok("Usuario auth eliminado correctamente");
    }
}
