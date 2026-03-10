package com.gestaoclinica.service;

import com.gestaoclinica.dto.PacienteRequestDTO;
import com.gestaoclinica.dto.PacienteResponseDTO;
import com.gestaoclinica.exception.exception_paciente.PacienteCampoObrigatorioException;
import com.gestaoclinica.exception.exception_paciente.PacienteCpfJaCadastradoException;
import com.gestaoclinica.exception.exception_paciente.PacienteNotFoundException;
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

    public PacienteResponseDTO salvar(PacienteRequestDTO dadosPaciente) {

        if (dadosPaciente.nome() == null || dadosPaciente.nome().isBlank()) {
            throw new PacienteCampoObrigatorioException("nome");
        }

        if (dadosPaciente.cpf() == null || dadosPaciente.cpf().isBlank()) {
            throw new PacienteCampoObrigatorioException("cpf");
        }

        if (repository.findByCpf(dadosPaciente.cpf()).isPresent()) {
            throw new PacienteCpfJaCadastradoException(dadosPaciente.cpf());
        }

        Paciente paciente = new Paciente();

        paciente.setNome(dadosPaciente.nome());
        paciente.setDataNasc(dadosPaciente.dataNasc());
        paciente.setCpf(dadosPaciente.cpf());
        paciente.setTelefone(dadosPaciente.telefone());
        paciente.setEndereco(dadosPaciente.endereco());
        paciente.setSexo(dadosPaciente.sexo());
        paciente.setEmail(dadosPaciente.email());

        paciente.setDataCadastro(LocalDate.now());

        Paciente pacienteSalvo = repository.save(paciente);

        return new PacienteResponseDTO(
                pacienteSalvo.getIdPaciente(),
                pacienteSalvo.getNome(),
                pacienteSalvo.getDataNasc(),
                pacienteSalvo.getCpf(),
                pacienteSalvo.getTelefone(),
                pacienteSalvo.getEndereco(),
                pacienteSalvo.getSexo(),
                pacienteSalvo.getDataCadastro(),
                pacienteSalvo.getEmail()
        );
    }

    public List<Paciente> listar() {
        return repository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException(id));
    }

    public void excluirPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new PacienteNotFoundException(id);
        }
        repository.deleteById(id);
    }
}