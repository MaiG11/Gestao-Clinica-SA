package com.gestaoclinica.service;

import com.gestaoclinica.dto.MedicoRequestDTO;
import com.gestaoclinica.dto.MedicoResponseDTO;
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

    // SALVAR MÉDICO
    public MedicoResponseDTO salvar(MedicoRequestDTO dadosMedico) {

        if (dadosMedico == null) {
            throw new IllegalArgumentException("Médico não pode ser nulo.");
        }

        if (dadosMedico.nome() == null || dadosMedico.nome().isBlank()) {
            throw new MedicoCampoObrigatorioException("nome");
        }

        if (dadosMedico.cpf() == null || dadosMedico.cpf().isBlank()) {
            throw new MedicoCampoObrigatorioException("cpf");
        }

        if (repository.findByCpf(dadosMedico.cpf()).isPresent()) {
            throw new MedicoCpfJaCadastradoException(dadosMedico.cpf());
        }

        if (dadosMedico.crm() == null || dadosMedico.crm().isBlank()) {
            throw new MedicoCampoObrigatorioException("crm");
        }

        if (repository.findByCrm(dadosMedico.crm()).isPresent()) {
            throw new MedicoCrmJaCadastradoException(dadosMedico.crm());
        }

        if (dadosMedico.especialidade() == null || dadosMedico.especialidade().isBlank()) {
            throw new MedicoCampoObrigatorioException("especialidade");
        }

        Medico medico = new Medico();

        medico.setNome(dadosMedico.nome());
        medico.setCpf(dadosMedico.cpf());
        medico.setEspecialidade(dadosMedico.especialidade());
        medico.setTelefone(dadosMedico.telefone());
        medico.setEmail(dadosMedico.email());
        medico.setCrm(dadosMedico.crm());

        medico.setDataAdmissao(LocalDate.now());

        Medico medicoSalvo = repository.save(medico);

        return new MedicoResponseDTO(
                medicoSalvo.getIdMedico(),
                medicoSalvo.getNome(),
                medicoSalvo.getCpf(),
                medicoSalvo.getEspecialidade(),
                medicoSalvo.getTelefone(),
                medicoSalvo.getEmail(),
                medicoSalvo.getCrm(),
                medicoSalvo.getDataAdmissao()
        );
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

    // EXCLUIR
    public void excluirPorId(Long id) {

        if (!repository.existsById(id)) {
            throw new MedicoNotFoundException(id);
        }

        repository.deleteById(id);
    }
}