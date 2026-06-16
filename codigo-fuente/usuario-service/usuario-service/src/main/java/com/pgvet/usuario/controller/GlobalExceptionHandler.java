package com.pgvet.usuario.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Manejo global de errores del microservicio
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Cuando el service lanza RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarRuntime(RuntimeException ex) {
        log.warn("Error de negocio: {}", ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(ex.getMessage());
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
