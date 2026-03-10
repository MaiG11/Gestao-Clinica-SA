package com.gestaoclinica;

import com.gestaoclinica.dto.ConsultaRequestDTO;
import com.gestaoclinica.dto.ConsultaResponseDTO;
import com.gestaoclinica.exception.exception_consulta.ConsultaCampoObrigatorioException;
import com.gestaoclinica.exception.exception_consulta.ConsultaNotFoundException;
import com.gestaoclinica.model.Consulta;
import com.gestaoclinica.model.Medico;
import com.gestaoclinica.model.Paciente;
import com.gestaoclinica.repository.ConsultaRepository;
import com.gestaoclinica.repository.MedicoRepository;
import com.gestaoclinica.repository.PacienteRepository;
import com.gestaoclinica.service.ConsultaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestaoClinicaApplicationTests {

    // Mock dos repositórios
    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private MedicoRepository medicoRepository;

    // Injeta os mocks no service
    @InjectMocks
    private ConsultaService service;

    // Deve salvar consulta quando os dados estiverem válidos
    @Test
    public void deveSalvarConsultaComDadosValidos() {

        Paciente paciente = new Paciente();
        paciente.setIdPaciente(1L);

        Medico medico = new Medico();
        medico.setIdMedico(1L);

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);

        ConsultaRequestDTO dto = new ConsultaRequestDTO(
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
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

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        ConsultaResponseDTO resultado = service.salvar(dto);

        assertNotNull(resultado);

        verify(consultaRepository).save(any(Consulta.class));
    }

    // Deve lançar exceção quando pacienteId for nulo
    @Test
    public void deveDarErroQuandoPacienteNaoExiste() {

        ConsultaRequestDTO dto = new ConsultaRequestDTO(
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
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

        assertThrows(ConsultaCampoObrigatorioException.class, () -> {
            service.salvar(dto);
        });
    }

    // Deve retornar consulta quando o ID existir
    @Test
    public void deveBuscarConsultaPorIdExistente() {

        Consulta consulta = new Consulta();

        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));

        Consulta resultado = service.buscarPorId(1L);

        assertNotNull(resultado);

        verify(consultaRepository).findById(1L);
    }

    // Deve lançar exceção quando consulta não existir
    @Test
    public void deveDarErroQuandoConsultaNaoExiste() {

        when(consultaRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ConsultaNotFoundException.class, () -> {
            service.buscarPorId(2L);
        });
    }

    // Deve excluir consulta quando existir
    @Test
   public void deveExcluirConsultaPorId() {

        when(consultaRepository.existsById(3L)).thenReturn(true);

        service.excluirPorId(3L);

        verify(consultaRepository).deleteById(3L);
    }
}