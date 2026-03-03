package com.gestaoclinica.exception.exception_medico;

public class MedicoNotFoundException extends RuntimeException {

    public MedicoNotFoundException(Long id) {
        super("Médico não encontrado com o id: " + id);
    }
}