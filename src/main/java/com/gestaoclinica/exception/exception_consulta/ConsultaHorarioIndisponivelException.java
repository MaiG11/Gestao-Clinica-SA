package com.gestaoclinica.exception.exception_consulta;

public class ConsultaHorarioIndisponivelException extends RuntimeException {

    public ConsultaHorarioIndisponivelException() {
        super("O médico já possui consulta neste dia e horário.");
    }
}