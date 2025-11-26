package com.gestaoclinica.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Paciente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;

    @Column(nullable = false, length = 150)
    private String nome;

    private LocalDate dataNasc;

    @Column(length = 20, unique = true)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Column(length = 200)
    private String endereco;

    @Column(length = 1)
    private String sexo; // CHAR(1)

    private LocalDate dataCadastro;

    @Column(length = 150)
    private String email;
}

