package com.gestaoclinica.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PacienteRequestDTO(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150)
    String nome,

    @NotNull(message = "Data de nascimento é obrigatória")
    LocalDate dataNasc,

    @NotBlank(message = "CPF é obrigatório")
    @Size(max = 20)
    String cpf,

    @Size(max = 20)
    String telefone,

    @Size(max = 200)
    String endereco,

    @Size(max = 1)
    String sexo,

    @Email(message = "Email inválido")
    @Size(max = 150)
    String email

) {}