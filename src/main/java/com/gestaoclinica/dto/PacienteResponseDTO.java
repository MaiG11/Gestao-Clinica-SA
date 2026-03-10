package com.gestaoclinica.dto;

import java.time.LocalDate;

public record PacienteResponseDTO(

    Long idPaciente,
    String nome,
    LocalDate dataNasc,
    String cpf,
    String telefone,
    String endereco,
    String sexo,
    LocalDate dataCadastro,
    String email

) {}
