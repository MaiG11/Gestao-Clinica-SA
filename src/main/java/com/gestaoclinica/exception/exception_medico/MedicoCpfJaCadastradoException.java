package com.gestaoclinica.exception.exception_medico;

public class MedicoCpfJaCadastradoException extends RuntimeException {

    public MedicoCpfJaCadastradoException(String cpf) {
        super("Já existe um médico cadastrado com o CPF: " + cpf);
    }
}