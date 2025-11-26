package com.gestaoclinica.model;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Consulta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsulta;

    private LocalDate dataConsulta;

    private LocalTime horario;

    // FK Paciente ----------------------
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    // FK Medico ------------------------
    @ManyToOne
    @JoinColumn(name = "id_medico")
    private Medico medico;

    @Column(length = 50)
    private String status;

    @Column(length = 100)
    private String tipoConsulta;

    @Column(length = 300)
    private String sintomas;

    @Column(length = 300)
    private String diagnostico;

    @Column(length = 300)
    private String medicacaoPrescrita;

    @Column(length = 1)
    private String retornoNecessario;

    private LocalDate dataRetorno;

    @Column(length = 300)
    private String alergiasRelevantes;

    @Column(length = 300)
    private String observacoes;
}
