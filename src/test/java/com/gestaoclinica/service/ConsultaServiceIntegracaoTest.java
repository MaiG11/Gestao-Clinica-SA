package com.gestaoclinica.service;

import com.gestaoclinica.dto.ConsultaRequestDTO;
import com.gestaoclinica.dto.ConsultaResponseDTO;
import com.gestaoclinica.exception.exception_consulta.ConsultaCampoObrigatorioException;
import com.gestaoclinica.exception.exception_consulta.ConsultaDataInvalidaException;
import com.gestaoclinica.exception.exception_consulta.ConsultaHorarioIndisponivelException;
import com.gestaoclinica.exception.exception_paciente.PacienteNotFoundException;
import com.gestaoclinica.model.Medico;
import com.gestaoclinica.model.Paciente;
import com.gestaoclinica.repository.MedicoRepository;
import com.gestaoclinica.repository.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ConsultaServiceIntegracaoTest {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    // salvar consulta válida
    @Test
    @DisplayName("Deve salvar consulta quando dados forem válidos")
    void deveSalvarConsulta() {

        Paciente paciente = new Paciente();
        paciente.setNome("Maira");
        paciente.setCpf("12345678900");
        pacienteRepository.save(paciente);

        Medico medico = new Medico();
        medico.setNome("Dr João");
        medico.setCpf("99999999999");
        medico.setCrm("CRM123");
        medicoRepository.save(medico);

        ConsultaRequestDTO dto = new ConsultaRequestDTO(
                LocalDate.now().plusDays(1),
                LocalTime.of(10,0),
                paciente.getIdPaciente(),
                medico.getIdMedico(),
                "AGENDADA",
                "Rotina",
                null,
                null,
                null,
                false,
                null,
                null,
                null
        );

        ConsultaResponseDTO resultado = consultaService.salvar(dto);

        assertNotNull(resultado);
        assertEquals(paciente.getIdPaciente(), resultado.pacienteId());
        assertEquals(medico.getIdMedico(), resultado.medicoId());
    }

    // erro quando pacienteId é nulo
    @Test
    @DisplayName("Deve lançar erro quando pacienteId for nulo")
    void deveLancarErroPacienteIdNulo() {

        ConsultaRequestDTO dto = new ConsultaRequestDTO(
                LocalDate.now().plusDays(1),
                LocalTime.of(10,0),
                null,
                1L,
                "AGENDADA",
                "Rotina",
                null,
                null,
                null,
                false,
                null,
                null,
                null
        );

        assertThrows(ConsultaCampoObrigatorioException.class,
                () -> consultaService.salvar(dto));
    }

    // erro quando paciente não existe
    @Test
    @DisplayName("Deve lançar erro quando paciente não existir")
    void deveLancarErroPacienteNaoExiste() {

        Medico medico = new Medico();
        medico.setNome("Dr João");
        medico.setCpf("99999999999");
        medico.setCrm("CRM123");
        medicoRepository.save(medico);

        ConsultaRequestDTO dto = new ConsultaRequestDTO(
                LocalDate.now().plusDays(1),
                LocalTime.of(10,0),
                999L,
                medico.getIdMedico(),
                "AGENDADA",
                "Rotina",
                null,
                null,
                null,
                false,
                null,
                null,
                null
        );

        assertThrows(PacienteNotFoundException.class,
                () -> consultaService.salvar(dto));
    }

    // erro quando data for no passado
    @Test
    @DisplayName("Deve lançar erro quando data da consulta for no passado")
    void deveLancarErroDataInvalida() {

        ConsultaRequestDTO dto = new ConsultaRequestDTO(
                LocalDate.now().minusDays(1),
                LocalTime.of(10,0),
                1L,
                1L,
                "AGENDADA",
                "Rotina",
                null,
                null,
                null,
                false,
                null,
                null,
                null
        );

        assertThrows(ConsultaDataInvalidaException.class,
                () -> consultaService.salvar(dto));
    }

    // erro quando horário já estiver ocupado
    @Test
    @DisplayName("Deve lançar erro quando horário já estiver ocupado")
    void deveLancarErroHorarioOcupado() {

        Paciente paciente = new Paciente();
        paciente.setNome("Maira");
        paciente.setCpf("12345678900");
        pacienteRepository.save(paciente);

        Medico medico = new Medico();
        medico.setNome("Dr João");
        medico.setCpf("99999999999");
        medico.setCrm("CRM123");
        medicoRepository.save(medico);

        ConsultaRequestDTO dto1 = new ConsultaRequestDTO(
                LocalDate.now().plusDays(1),
                LocalTime.of(10,0),
                paciente.getIdPaciente(),
                medico.getIdMedico(),
                "AGENDADA",
                "Rotina",
                null,
                null,
                null,
                false,
                null,
                null,
                null
        );

        consultaService.salvar(dto1);

        ConsultaRequestDTO dto2 = new ConsultaRequestDTO(
                LocalDate.now().plusDays(1),
                LocalTime.of(10,0),
                paciente.getIdPaciente(),
                medico.getIdMedico(),
                "AGENDADA",
                "Rotina",
                null,
                null,
                null,
                false,
                null,
                null,
                null
        );

        assertThrows(ConsultaHorarioIndisponivelException.class,
                () -> consultaService.salvar(dto2));
    }

    // excluir consulta
    @Test
    @DisplayName("Deve excluir consulta existente")
    void deveExcluirConsulta() {

        Paciente paciente = new Paciente();
        paciente.setNome("Maira");
        paciente.setCpf("12345678900");
        pacienteRepository.save(paciente);

        Medico medico = new Medico();
        medico.setNome("Dr João");
        medico.setCpf("99999999999");
        medico.setCrm("CRM123");
        medicoRepository.save(medico);

        ConsultaRequestDTO dto = new ConsultaRequestDTO(
                LocalDate.now().plusDays(1),
                LocalTime.of(10,0),
                paciente.getIdPaciente(),
                medico.getIdMedico(),
                "AGENDADA",
                "Rotina",
                null,
                null,
                null,
                false,
                null,
                null,
                null
        );

        ConsultaResponseDTO consulta = consultaService.salvar(dto);

        consultaService.excluirPorId(consulta.idConsulta());

        assertThrows(RuntimeException.class,
                () -> consultaService.buscarPorId(consulta.idConsulta()));
    }
}