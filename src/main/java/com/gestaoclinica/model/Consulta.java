package com.gestaoclinica.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    // ---------------- RELACIONAMENTO PACIENTE ----------------
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    // ---------------- RELACIONAMENTO MEDICO ------------------
    @ManyToOne(fetch = FetchType.EAGER)
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

    @Column(nullable = false)
    private Boolean retornoNecessario;

    private LocalDate dataRetorno;

    @Column(length = 300)
    private String alergiasRelevantes;

    @Column(length = 300)
    private String observacoes;

    // ---------------- CONSTRUTOR VAZIO ----------------
    public Consulta() {}

    // ---------------- GETTERS E SETTERS ----------------

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getMedicacaoPrescrita() {
        return medicacaoPrescrita;
    }

    public void setMedicacaoPrescrita(String medicacaoPrescrita) {
        this.medicacaoPrescrita = medicacaoPrescrita;
    }

    // ✅ CORREÇÃO AQUI
    public Boolean getRetornoNecessario() {
        return retornoNecessario;
    }

    // ✅ CORREÇÃO AQUI
    public void setRetornoNecessario(Boolean retornoNecessario) {
        this.retornoNecessario = retornoNecessario;
    }

    public LocalDate getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(LocalDate dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public String getAlergiasRelevantes() {
        return alergiasRelevantes;
    }

    public void setAlergiasRelevantes(String alergiasRelevantes) {
        this.alergiasRelevantes = alergiasRelevantes;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
