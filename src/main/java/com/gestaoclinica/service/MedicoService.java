package com.gestaoclinica.service;

import com.gestaoclinica.model.Medico;
import com.gestaoclinica.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    // SALVAR COM TODAS AS REGRAS DE NEGÓCIO
    public Medico salvar(Medico medico) {

        // Nome obrigatório
        if (medico.getNome() == null || medico.getNome().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        // CPF obrigatório
        if (medico.getCpf() == null || medico.getCpf().isEmpty()) {
            throw new RuntimeException("CPF é obrigatório");
        }

        // CPF não pode repetir
        if (repository.findByCpf(medico.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado");
        }

        // CRM obrigatório
        if (medico.getCrm() == null || medico.getCrm().isEmpty()) {
            throw new RuntimeException("CRM é obrigatório");
        }

        // CRM não pode repetir
        if (repository.findByCrm(medico.getCrm()).isPresent()) {
            throw new RuntimeException("CRM já cadastrado");
        }

        // Especialidade obrigatória
        if (medico.getEspecialidade() == null || medico.getEspecialidade().isEmpty()) {
            throw new RuntimeException("Especialidade é obrigatória");
        }

        // Data de admissão automática
        medico.setDataAdmissao(LocalDate.now());

        return repository.save(medico);
    }

    // LISTAR TODOS
    public List<Medico> listar() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    public Medico buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
    }

    // BUSCAR POR ESPECIALIDADE
    public List<Medico> buscarPorEspecialidade(String especialidade) {
        return repository.findByEspecialidade(especialidade);
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
