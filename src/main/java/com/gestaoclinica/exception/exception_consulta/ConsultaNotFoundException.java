package com.gestaoclinica.exception.exception_consulta;

public class ConsultaNotFoundException extends RuntimeException {

    public ConsultaNotFoundException(Long id) {
        super("Consulta com ID " + id + " não encontrada.");
    }
}