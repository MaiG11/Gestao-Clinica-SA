package com.gestaoclinica.exception.exceptionPaciente;

import com.gestaoclinica.exception.RequestException;

public class DuplicateCpfException extends RequestException {
    public DuplicateCpfException(String cpf) {
        super("CpfDuplicado", "CPF já cadastrado: " + cpf);
    }
}
