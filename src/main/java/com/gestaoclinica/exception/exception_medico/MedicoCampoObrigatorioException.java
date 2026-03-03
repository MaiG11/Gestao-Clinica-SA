package com.gestaoclinica.exception.exception_medico;

public class MedicoCampoObrigatorioException extends RuntimeException {

    public MedicoCampoObrigatorioException(String campo) {
        super("O campo " + campo + " é obrigatório.");
    }
}