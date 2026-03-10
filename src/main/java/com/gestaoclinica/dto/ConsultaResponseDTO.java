package com.gestaoclinica.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ConsultaResponseDTO(

    Long idConsulta,

    LocalDate dataConsulta,

    LocalTime horario,

    Long pacienteId,
    String pacienteNome,

    Long medicoId,
    String medicoNome,

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