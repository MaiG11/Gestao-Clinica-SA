package com.gestaoclinica.service;

import com.gestaoclinica.model.Consulta;
import com.gestaoclinica.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    // SALVAR COM REGRA
    public Consulta salvar(Consulta consulta) {

        // Não permite salvar sem paciente
        if (consulta.getPaciente() == null) {
            throw new RuntimeException("Paciente é obrigatório");
        }

        // Não permite salvar sem médico
        if (consulta.getMedico() == null) {
            throw new RuntimeException("Médico é obrigatório");
        }

        // Se tiver retorno = true, a data é obrigatória
        if (consulta.getRetornoNecessario() != null && consulta.getRetornoNecessario()
                && consulta.getDataRetorno() == null) {
            throw new RuntimeException("Data de retorno é obrigatória");
        }

        return repository.save(consulta);
    }

    // LISTAR TODOS
    public List<Consulta> listar() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    public Consulta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }

    // EXCLUIR POR ID
    public void excluirPorId(Long id) {
        repository.deleteById(id);
    }

    // EXCLUIR TODOS
    public void excluirTodos() {
        repository.deleteAll();
    }
}
