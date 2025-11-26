package com.gestaoclinica.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Medico {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedico;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 14, unique = true)
    private String cpf;

    @Column(length = 100)
    private String especialidade;

    @Column(length = 20)
    private String telefone;

    @Column(length = 150)
    private String email;

    @Column(length = 30, unique = true)
    private String crm;

    private LocalDate dataAdmissao;
}
