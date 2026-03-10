package com.gestaoclinica.repository;

import com.gestaoclinica.model.Consulta;
import com.gestaoclinica.model.Medico;
import com.gestaoclinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Buscar consultas por paciente
    List<Consulta> findByPaciente(Paciente paciente);

    // Buscar consultas por médico
    List<Consulta> findByMedico(Medico medico);

    // Buscar consultas por status
    List<Consulta> findByStatus(String status);

    // Verifica se médico já tem consulta no mesmo dia e horário
    boolean existsByMedicoAndDataConsultaAndHorario(
            Medico medico,
            LocalDate dataConsulta,
            LocalTime horario
    );
}