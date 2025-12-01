package com.gestaoclinica.repository;

import com.gestaoclinica.model.Consulta;
import com.gestaoclinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Buscar consultas por paciente
    List<Consulta> findByPaciente(Paciente paciente);

    // Buscar consultas por status
    List<Consulta> findByStatus(String status);
}
