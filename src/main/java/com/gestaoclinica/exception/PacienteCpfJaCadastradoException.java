package com.gestaoclinica.exception;

public class PacienteCpfJaCadastradoException extends RequestException {
    public PacienteCpfJaCadastradoException(String cpf) {
        super("CpfJaCadastrado", "O CPF " + cpf + " já está cadastrado");
    }
}
