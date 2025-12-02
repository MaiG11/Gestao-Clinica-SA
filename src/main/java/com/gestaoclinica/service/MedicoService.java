package com.gestaoclinica.service;

import com.gestaoclinica.model.Medico;
import com.gestaoclinica.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    // SALVAR COM REGRA
    public Medico salvar(Medico medico) {

        // CRM não pode ser vazio
        if (medico.getCrm() == null || medico.getCrm().isEmpty()) {
            throw new RuntimeException("CRM é obrigatório");
        }

        // CRM não pode repetir
        if (repository.findByCrm(medico.getCrm()).isPresent()) {
            throw new RuntimeException("CRM já cadastrado");
        }

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

    // EXCLUIR POR ID
    public void excluirPorId(Long id) {
        repository.deleteById(id);
    }

    // EXCLUIR TODOS
    public void excluirTodos() {
        repository.deleteAll();
    }
}
