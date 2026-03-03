package com.gestaoclinica.exception.exception_medico;

public class MedicoEspecialidadeNaoEncontradaException extends RuntimeException {

    public MedicoEspecialidadeNaoEncontradaException(String especialidade) {
        super("Nenhum médico encontrado para a especialidade: " + especialidade);
    }
}