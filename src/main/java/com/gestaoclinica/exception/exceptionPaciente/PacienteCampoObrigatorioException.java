package com.gestaoclinica.exception.exceptionPaciente;

import com.gestaoclinica.exception.RequestException;

public class PacienteCampoObrigatorioException extends RequestException {
    public PacienteCampoObrigatorioException(String campo) {
        super("CampoObrigatorio", "O campo '" + campo + "' é obrigatório");
    }
}
