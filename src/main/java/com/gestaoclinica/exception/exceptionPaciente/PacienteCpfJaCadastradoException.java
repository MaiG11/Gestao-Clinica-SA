package com.gestaoclinica.exception.exceptionPaciente;

public class PacienteCpfJaCadastradoException extends RuntimeException {

    public PacienteCpfJaCadastradoException(String cpf) {
        super("Já existe um paciente cadastrado com o CPF: " + cpf);
    }
}