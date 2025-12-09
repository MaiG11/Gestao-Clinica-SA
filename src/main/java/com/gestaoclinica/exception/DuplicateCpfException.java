package com.gestaoclinica.exception;

public class DuplicateCpfException extends RequestException {
    public DuplicateCpfException(String cpf) {
        super("CpfDuplicado", "CPF já cadastrado: " + cpf);
    }
}
