package com.gestaoclinica.exception.exception_consulta;

public class ConsultaDataInvalidaException extends RuntimeException {

    public ConsultaDataInvalidaException() {
        super("Não é permitido agendar consulta com data passada.");
    }
}