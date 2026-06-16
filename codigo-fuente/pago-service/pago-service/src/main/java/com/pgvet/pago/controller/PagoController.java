package com.pgvet.pago.controller;

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

import com.pgvet.pago.dto.PagoCreateDTO;
import com.pgvet.pago.dto.PagoDTO;
import com.pgvet.pago.service.PagoService;

// Controlador REST de pagos
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // GET -> listar pagos
    @GetMapping
    public ResponseEntity<List<PagoDTO>> listar() {
        return ResponseEntity.ok(pagoService.listar());
    }

    // GET -> buscar pago por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {

        Optional<PagoDTO> pago = pagoService.buscarPorId(id);

        if (pago.isPresent()) {
            return ResponseEntity.ok(pago.get());
        }

        return ResponseEntity
                .status(404)
                .body("Pago no encontrado");
    }

    // POST -> crear pago
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody PagoCreateDTO dto) {

        PagoDTO nuevoPago = pagoService.guardar(dto);

        return ResponseEntity
                .status(201)
                .body(nuevoPago);
    }

    // PUT -> actualizar pago
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PagoCreateDTO dto) {

        PagoDTO pagoActualizado = pagoService.actualizar(id, dto);

        return ResponseEntity.ok(pagoActualizado);
    }

    // DELETE -> eliminar pago
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        pagoService.eliminar(id);

        return ResponseEntity.ok("Pago eliminado correctamente");
    }
}
