package com.gestaoclinica.exception;

public class PacienteNotFoundException extends RequestException {
    public PacienteNotFoundException(Long id) {
        super("PacienteNaoEncontrado", "Paciente não encontrado com ID: " + id);
    }
}
