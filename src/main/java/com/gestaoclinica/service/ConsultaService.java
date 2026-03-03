package com.gestaoclinica.service;

import com.gestaoclinica.exception.exception_consulta.ConsultaCampoObrigatorioException;
import com.gestaoclinica.exception.exception_consulta.ConsultaDataInvalidaException;
import com.gestaoclinica.exception.exception_consulta.ConsultaHorarioIndisponivelException;
import com.gestaoclinica.exception.exception_consulta.ConsultaNotFoundException;
import com.gestaoclinica.model.Consulta;
import com.gestaoclinica.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public Consulta salvar(Consulta consulta) {

        if (consulta.getPaciente() == null) {
            throw new ConsultaCampoObrigatorioException("paciente");
        }

        if (consulta.getMedico() == null) {
            throw new ConsultaCampoObrigatorioException("medico");
        }

        if (consulta.getDataConsulta() == null) {
            throw new ConsultaCampoObrigatorioException("dataConsulta");
        }

        if (consulta.getHorario() == null) {
            throw new ConsultaCampoObrigatorioException("horario");
        }

        //  Não permitir data passada
        if (consulta.getDataConsulta().isBefore(LocalDate.now())) {
            throw new ConsultaDataInvalidaException();
}
        if (consulta.getRetornoNecessario() == null) {
            throw new ConsultaCampoObrigatorioException("retornoNecessario");
        }

        if (consulta.getRetornoNecessario()
                && consulta.getDataRetorno() == null) {
            throw new ConsultaCampoObrigatorioException("dataRetorno");
        }

        //  Verifica conflito de horário do médico
        if (repository.existsByMedicoAndDataConsultaAndHorario(
                consulta.getMedico(),
                consulta.getDataConsulta(),
                consulta.getHorario())) {

            throw new ConsultaHorarioIndisponivelException();
        }

        return repository.save(consulta);
    }

    public List<Consulta> listar() {
        return repository.findAll();
    }

    public Consulta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ConsultaNotFoundException(id));
    }

    public void excluirPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new ConsultaNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public void excluirTodos() {
        repository.deleteAll();
    }
}