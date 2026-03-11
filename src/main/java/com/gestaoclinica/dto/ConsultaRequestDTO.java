package com.gestaoclinica.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConsultaRequestDTO(

    @NotNull(message = "Data da consulta é obrigatória")
    LocalDate dataConsulta,

    @NotNull(message = "Horário da consulta é obrigatório")
    LocalTime horario,

    @NotNull(message = "Paciente é obrigatório")
    Long pacienteId,

    @NotNull(message = "Médico é obrigatório")
    Long medicoId,

    @Size(max = 50)
    String status,

    @Size(max = 100)
    String tipoConsulta,

    @Size(max = 300)
    String sintomas,

    @Size(max = 300)
    String diagnostico,

    @Size(max = 300)
    String medicacaoPrescrita,

    @NotNull(message = "Retorno necessário é obrigatório")
    Boolean retornoNecessario,

    LocalDate dataRetorno,

    @Size(max = 300)
    String alergiasRelevantes,

    @Size(max = 300)
    String observacoes

) {}