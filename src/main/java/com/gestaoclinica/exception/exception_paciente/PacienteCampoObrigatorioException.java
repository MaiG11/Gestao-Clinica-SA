package com.gestaoclinica.exception.exception_paciente;

public class PacienteCampoObrigatorioException extends RuntimeException {

    public PacienteCampoObrigatorioException(String campo) {
        super("O campo " + campo + " é obrigatório.");
    }
}