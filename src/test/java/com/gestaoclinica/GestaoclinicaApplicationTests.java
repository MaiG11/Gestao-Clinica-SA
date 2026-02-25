
package com.gestaoclinica;

import com.gestaoclinica.model.Consulta;
import com.gestaoclinica.model.Medico;
import com.gestaoclinica.model.Paciente;
import com.gestaoclinica.repository.ConsultaRepository;
import com.gestaoclinica.service.ConsultaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestaoClinicaApplicationTests {

    // Mock do repositório para isolar a regra de negócio do service
    @Mock
    private ConsultaRepository repository;

    // Injeta o mock no service que será testado
    @InjectMocks
    private ConsultaService service;


    // Deve salvar consulta quando os dados obrigatórios estiverem válidos
    @Test
    void deveSalvarConsultaComDadosValidos() {

        Paciente paciente = new Paciente();
        Medico medico = new Medico();
        Consulta consulta = new Consulta();

        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setRetornoNecessario(false);

        // Simula retorno do repository.save()
        when(repository.save(consulta)).thenReturn(consulta);

        Consulta resultado = service.salvar(consulta);

        assertNotNull(resultado);

        // Garante que o save foi chamado
        verify(repository).save(consulta);
    }


    // Deve lançar exceção ao tentar salvar consulta sem paciente
    @Test
    void deveDarErroQuandoPacienteNaoExiste() {

        Medico medico = new Medico();
        Consulta consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setRetornoNecessario(false);

        // Espera RuntimeException ao salvar consulta inválida
        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            service.salvar(consulta);
        });

        assertEquals("Paciente é obrigatório", erro.getMessage());
    }


    // Deve retornar consulta quando o ID existir
    @Test
    void deveBuscarConsultaPorIdExistente() {

        Consulta consulta = new Consulta();

        // Simula retorno do repository.findById()
        when(repository.findById(1L)).thenReturn(Optional.of(consulta));

        Consulta resultado = service.buscarPorId(1L);

        assertNotNull(resultado);

        // Verifica se o método foi chamado com o ID correto
        verify(repository).findById(1L);
    }


    // Deve lançar exceção quando consulta não existir
    @Test
    void deveDarErroQuandoConsultaNaoExiste() {

        // Simula ID inexistente
        when(repository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException erro = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(2L);
        });

        assertEquals("Consulta não encontrada", erro.getMessage());
    }


    // Deve chamar o deleteById ao excluir consulta
    @Test
    void deveExcluirConsultaPorId() {

        service.excluirPorId(3L);

        // Garante que o repository.deleteById foi chamado
        verify(repository).deleteById(3L);
    }
}