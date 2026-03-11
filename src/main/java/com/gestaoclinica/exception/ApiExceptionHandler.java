package com.gestaoclinica.exception;

import com.gestaoclinica.exception.exception_consulta.*;
import com.gestaoclinica.exception.exception_medico.*;
import com.gestaoclinica.exception.exception_paciente.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException; // Para validação @Valid
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    // 🔴 404 - Não encontrado
    @ExceptionHandler({
            ConsultaNotFoundException.class,
            MedicoNotFoundException.class,
            PacienteNotFoundException.class
    })
    public ResponseEntity<?> handleNotFound(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }

    // 🟡 409 - Conflitos (duplicidade ou horário ocupado)
    @ExceptionHandler({
            ConsultaHorarioIndisponivelException.class,
            MedicoCpfJaCadastradoException.class,
            MedicoCrmJaCadastradoException.class,
            PacienteCpfJaCadastradoException.class
    })
    public ResponseEntity<?> handleConflict(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    // 🟢 400 - Erros de validação customizados
    @ExceptionHandler({
            ConsultaCampoObrigatorioException.class,
            ConsultaDataInvalidaException.class,
            MedicoCampoObrigatorioException.class,
            PacienteCampoObrigatorioException.class
    })
    public ResponseEntity<?> handleBadRequest(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("message", ex.getMessage()));
    }

    // 🔵 Erros de validação do @Valid nos DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // Para cada campo com erro, pega o nome do campo e a mensagem
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
                .badRequest()
                .body(errors); // Retorna mapa campo -> mensagem
    }

    // 🔵 Fallback - erro inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Erro interno no servidor"));
    }
}