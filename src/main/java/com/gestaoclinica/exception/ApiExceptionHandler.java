package com.gestaoclinica.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    // Caso ainda use RequestException em algum lugar
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<?> handleRequestException(RequestException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "code", ex.getCode(),
                        "message", ex.getMessage()
                ));
    }

    // 🔹 Trata TODAS as exceptions personalizadas (Paciente e Medico)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "message", ex.getMessage()
                ));
    }
}