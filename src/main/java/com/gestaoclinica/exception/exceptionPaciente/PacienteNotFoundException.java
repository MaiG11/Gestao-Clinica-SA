package com.gestaoclinica.exception.exceptionPaciente;

import com.gestaoclinica.exception.RequestException;

public class PacienteNotFoundException extends RequestException {
    public PacienteNotFoundException(Long id) {
        super("PacienteNaoEncontrado", "Paciente não encontrado com ID: " + id);
    }
}
