package com.gestaoclinica.repository;

import com.gestaoclinica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // Buscar médico pelo CPF
    Optional<Medico> findByCpf(String cpf);

    // Buscar médico pelo CRM
    Optional<Medico> findByCrm(String crm);

    // Buscar médicos pela especialidade
    List<Medico> findByEspecialidade(String especialidade);
}
