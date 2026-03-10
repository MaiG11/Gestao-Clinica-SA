package com.gestaoclinica.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ConsultaRequestDTO(

    LocalDate dataConsulta,

    LocalTime horario,

    Long pacienteId,

    Long medicoId,

    String status,

    String tipoConsulta,

    String sintomas,

    String diagnostico,

    String medicacaoPrescrita,

    Boolean retornoNecessario,

    LocalDate dataRetorno,

    String alergiasRelevantes,

    String observacoes

) {}