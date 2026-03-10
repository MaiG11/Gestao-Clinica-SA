package com.gestaoclinica.dto;

import java.time.LocalDate;

public record MedicoResponseDTO(

        Long idMedico,
        String nome,
        String cpf,
        String especialidade,
        String telefone,
        String email,
        String crm,
        LocalDate dataAdmissao

) {}

