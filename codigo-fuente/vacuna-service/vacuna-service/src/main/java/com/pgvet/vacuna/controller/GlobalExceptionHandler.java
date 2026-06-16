package com.pgvet.vacuna.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Manejo global de errores del microservicio (estilo estudiante)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Cuando el service lanza RuntimeException (404 negocio o 503 Feign)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarRuntime(RuntimeException ex) {
        String mensaje = ex.getMessage() != null ? ex.getMessage() : "Error";

        if (mensaje.startsWith("No se pudo conectar")) {
            log.warn("Servicio externo no disponible: {}", mensaje);
            return ResponseEntity.status(503).body(mensaje);
        }

        log.warn("Error de negocio: {}", mensaje);
        return ResponseEntity.status(404).body(mensaje);
    }

    // Cuando fallan las validaciones del DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> manejarErrores(MethodArgumentNotValidException ex) {
        log.warn("Hay campos vacíos o inválidos en la petición");
        return ResponseEntity
                .badRequest()
                .body("Hay campos vacíos o inválidos");
    }
}
