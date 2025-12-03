package com.gestaoclinica.service;

import com.gestaoclinica.model.Paciente;
import com.gestaoclinica.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    // SALVAR COM REGRA
    public Paciente salvar(Paciente paciente) {

        // Nome obrigatório
        if (paciente.getNome() == null || paciente.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        // CPF obrigatório
        if (paciente.getCpf() == null || paciente.getCpf().isEmpty()) {
            throw new RuntimeException("CPF é obrigatório");
        }

        // CPF não pode repetir
        if (repository.findByCpf(paciente.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado");
        }

        // Define data de cadastro automaticamente
        paciente.setDataCadastro(LocalDate.now());

        return repository.save(paciente);
    }

    // LISTAR TODOS
    public List<Paciente> listar() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    public Paciente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
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
