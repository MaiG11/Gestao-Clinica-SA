package com.gestaoclinica.exception.exception_consulta;

public class ConsultaCampoObrigatorioException extends RuntimeException {

    public ConsultaCampoObrigatorioException(String campo) {
        super("O campo " + campo + " é obrigatório.");
    }
}