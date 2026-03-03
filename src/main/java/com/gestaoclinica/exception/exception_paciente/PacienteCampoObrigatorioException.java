package com.gestaoclinica.exception.exception_paciente;

import com.gestaoclinica.exception.RequestException;

public class PacienteCampoObrigatorioException extends RequestException {
    public PacienteCampoObrigatorioException(String campo) {
        super("CampoObrigatorio", "O campo '" + campo + "' é obrigatório");
    }
}
