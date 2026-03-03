package com.gestaoclinica.exception.exception_medico;

public class MedicoCrmJaCadastradoException extends RuntimeException {

    public MedicoCrmJaCadastradoException(String crm) {
        super("Já existe um médico cadastrado com o CRM: " + crm);
    }
}