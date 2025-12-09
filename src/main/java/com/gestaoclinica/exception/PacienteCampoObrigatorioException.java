package com.gestaoclinica.exception;

public class PacienteCampoObrigatorioException extends RequestException {
    public PacienteCampoObrigatorioException(String campo) {
        super("CampoObrigatorio", "O campo '" + campo + "' é obrigatório");
    }
}
