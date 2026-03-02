package com.gestaoclinica.service;

import com.gestaoclinica.exception.exceptionPaciente.PacienteCampoObrigatorioException;
import com.gestaoclinica.exception.exceptionPaciente.PacienteCpfJaCadastradoException;
import com.gestaoclinica.exception.exceptionPaciente.PacienteNotFoundException;
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

    public Paciente salvar(Paciente paciente) {

        if (paciente.getNome() == null || paciente.getNome().isBlank()) {
            throw new PacienteCampoObrigatorioException("nome");
        }

        if (paciente.getCpf() == null || paciente.getCpf().isBlank()) {
            throw new PacienteCampoObrigatorioException("cpf");
        }

        if (repository.findByCpf(paciente.getCpf()).isPresent()) {
            throw new PacienteCpfJaCadastradoException(paciente.getCpf());
        }

        paciente.setDataCadastro(LocalDate.now());
        return repository.save(paciente);
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
