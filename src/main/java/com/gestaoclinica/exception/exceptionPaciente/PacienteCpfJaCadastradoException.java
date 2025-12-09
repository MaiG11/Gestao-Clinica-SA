package com.gestaoclinica.exception.exceptionPaciente;

import com.gestaoclinica.exception.RequestException;

public class PacienteCpfJaCadastradoException extends RequestException {
    public PacienteCpfJaCadastradoException(String cpf) {
        super("CpfJaCadastrado", "O CPF " + cpf + " já está cadastrado");
    }
}
