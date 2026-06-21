package com.pgvet.pago.controller;

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

import com.pgvet.pago.dto.PagoCreateDTO;
import com.pgvet.pago.dto.PagoDTO;
import com.pgvet.pago.service.PagoService;

@Tag(name = "Pagos", description = "Operaciones de registro y consulta de pagos")
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(summary = "Listar pagos",
               description = "Retorna todos los pagos registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<PagoDTO>> listar() {
        return ResponseEntity.ok(pagoService.listar());
    }

    @Operation(summary = "Buscar pago por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID único del pago", required = true)
            @PathVariable Long id) {

        Optional<PagoDTO> pago = pagoService.buscarPorId(id);

        if (pago.isPresent()) {
            return ResponseEntity.ok(pago.get());
        }

        return ResponseEntity
                .status(404)
                .body("Pago no encontrado");
    }

    @Operation(summary = "Registrar nuevo pago")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pago creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario o cita no encontrados"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody PagoCreateDTO dto) {

        PagoDTO nuevoPago = pagoService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevoPago);
    }

    @Operation(summary = "Actualizar pago existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Actualización exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Pago o referencia externa no encontrada"),
        @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @Parameter(description = "ID único del pago", required = true)
            @PathVariable Long id,
            @Valid @RequestBody PagoCreateDTO dto) {

        PagoDTO pagoActualizado = pagoService.actualizar(id, dto);

        return ResponseEntity.ok(pagoActualizado);
    }

    @Operation(summary = "Eliminar pago")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Eliminación exitosa"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID único del pago", required = true)
            @PathVariable Long id) {

        pagoService.eliminar(id);

        return ResponseEntity.ok("Pago eliminado correctamente");
    }
}
