package com.gestaoclinica.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicoRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150)
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @Size(max = 20)
        String cpf,

        @NotBlank(message = "Especialidade é obrigatória")
        @Size(max = 100)
        String especialidade,

        @Size(max = 20)
        String telefone,

        @Email(message = "Email inválido")
        @Size(max = 150)
        String email,

        @NotBlank(message = "CRM é obrigatório")
        @Size(max = 20)
        String crm,

        @NotNull(message = "Data de admissão é obrigatória")
        LocalDate dataAdmissao

) {}