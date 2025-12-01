package com.gestaoclinica.repository;

import com.gestaoclinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Buscar paciente pelo CPF
    Optional<Paciente> findByCpf(String cpf);

    // Buscar paciente pelo nome (contém)
    List<Paciente> findByNomeContaining(String nome);
}
