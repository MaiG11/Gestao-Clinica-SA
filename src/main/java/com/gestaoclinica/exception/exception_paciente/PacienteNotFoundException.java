package com.gestaoclinica.exception.exception_paciente;

public class PacienteNotFoundException extends RuntimeException {

    public PacienteNotFoundException(Long id) {
        super("Paciente não encontrado com o ID: " + id);
    }
}