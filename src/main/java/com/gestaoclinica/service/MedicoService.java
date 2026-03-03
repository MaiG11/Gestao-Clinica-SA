package com.gestaoclinica.service;

import com.gestaoclinica.exception.exception_medico.MedicoCampoObrigatorioException;
import com.gestaoclinica.exception.exception_medico.MedicoCpfJaCadastradoException;
import com.gestaoclinica.exception.exception_medico.MedicoCrmJaCadastradoException;
import com.gestaoclinica.exception.exception_medico.MedicoEspecialidadeNaoEncontradaException;
import com.gestaoclinica.exception.exception_medico.MedicoNotFoundException;
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

    if (medico == null) {
        throw new IllegalArgumentException("Médico não pode ser nulo.");
    }

    if (medico.getNome() == null || medico.getNome().isBlank()) {
        throw new MedicoCampoObrigatorioException("nome");
    }

    if (medico.getCpf() == null || medico.getCpf().isBlank()) {
        throw new MedicoCampoObrigatorioException("cpf");
    }

    if (repository.findByCpf(medico.getCpf()).isPresent()) {
        throw new MedicoCpfJaCadastradoException(medico.getCpf());
    }

    if (medico.getCrm() == null || medico.getCrm().isBlank()) {
        throw new MedicoCampoObrigatorioException("crm");
    }

    if (repository.findByCrm(medico.getCrm()).isPresent()) {
        throw new MedicoCrmJaCadastradoException(medico.getCrm());
    }

    if (medico.getEspecialidade() == null || medico.getEspecialidade().isBlank()) {
        throw new MedicoCampoObrigatorioException("especialidade");
    }

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
                .orElseThrow(() -> new MedicoNotFoundException(id));
    }

    // BUSCAR POR ESPECIALIDADE
    public List<Medico> buscarPorEspecialidade(String especialidade) {

        if (especialidade == null || especialidade.isBlank()) {
            throw new MedicoCampoObrigatorioException("especialidade");
        }

        List<Medico> lista = repository.findByEspecialidade(especialidade);

        if (lista.isEmpty()) {
            throw new MedicoEspecialidadeNaoEncontradaException(especialidade);
        }

        return lista;
    }

    // EXCLUIR POR ID
    public void excluirPorId(Long id) {

        if (!repository.existsById(id)) {
            throw new MedicoNotFoundException(id);
        }

        repository.deleteById(id);
    }
}